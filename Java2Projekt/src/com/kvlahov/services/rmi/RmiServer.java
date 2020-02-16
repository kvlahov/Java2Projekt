/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvlahov.services.rmi;

import com.kvlahov.model.ReservationInfo;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.kvlahov.utilities.PropertiesManager;
import java.util.function.Predicate;

/**
 *
 * @author evlakre
 */
public class RmiServer implements IRmiServer {

    private static final Logger LOG = Logger.getLogger(RmiServer.class.getName());
    private List<IRmiClient> clients;
    private static long clientId;
    private static Registry registry;

    private List<Integer> reservedTables;
    private List<Integer> unavailableTables;

    public RmiServer() {
        clients = new ArrayList<>();
        reservedTables = new ArrayList<>();
        unavailableTables = new ArrayList<>();
    }

    public static void main(String[] args) {
        try {
            RmiServer obj = new RmiServer();
            PropertiesManager pm = new PropertiesManager();
            IRmiServer stub = (IRmiServer) UnicastRemoteObject.exportObject(obj, pm.getServerPort());

            registry = LocateRegistry.createRegistry(pm.getServerPort());
            registry.rebind(pm.getServerUrl(), stub);

            LOG.info("Server ready");

            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    for (IRmiClient client : obj.getClientsArray()) {
                        try {
                            client.getClientId();
                        } catch (RemoteException ex) {
                            try {
                                obj.unregisterClient(client);
                            } catch (RemoteException ex1) {
                                Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                    }
                }

            }).start();

        } catch (RemoteException ex) {
            Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized IRmiClient[] getClientsArray() {
        IRmiClient[] clientsArray = new IRmiClient[clients.size()];
        for (int i = 0; i < clients.size(); i++) {
            clientsArray[i] = clients.get(i);
        }
        
        return clientsArray;
    }

    @Override
    public synchronized void registerClient(IRmiClient client) throws RemoteException {
        if (!clients.contains(client)) {
            long newId = clients.stream().mapToLong(c -> {
                try {
                    return c.getClientId();
                } catch (RemoteException ex) {
                    Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                    return -1;
                }
            }).max().orElse(0) + 1;

            if (newId == 0) {
                return;
            }

            client.setClientId(newId);
            clients.add(client);
            LOG.info("Added client: " + client.getClientId());

            unavailableTables.forEach(t -> {
                try {
                    client.notifyTableLocked(t);
                } catch (RemoteException ex) {
                    Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            reservedTables.forEach(t -> {
                try {
                    client.notifyTableReserved(t);
                } catch (RemoteException ex) {
                    Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            });


            notifyClientNumberChanged(clients.size());
            
                        
        }
    }

    @Override
    public synchronized void unregisterClient(IRmiClient client) throws RemoteException {
        if (clients.contains(client)) {
            clients.remove(client);
            notifyClientNumberChanged(clients.size());

            LOG.info("Removed client: ");
        }
    }

    @Override
    public synchronized void lockTable(long clientId, int id) throws RemoteException {
        if(unavailableTables.contains(id)) {
            return;
        }
        unavailableTables.add(id);
        clients.stream()
                .filter(c -> {
                    try {
                        return c.getClientId() != clientId;
                    } catch (RemoteException ex) {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                })
                .forEach(c -> {
                    try {
                        c.notifyTableLocked(id);
                    } catch (RemoteException ex) {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
    }

    @Override
    public synchronized void unlockTable(long clientId, int id) throws RemoteException {
        clients.stream()
                .filter(c -> {
                    try {
                        return c.getClientId() != clientId;
                    } catch (RemoteException ex) {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                })
                .forEach(c -> {
                    try {
                        c.notifyTableUnlocked(id);
                    } catch (RemoteException ex) {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
    }

    @Override
    public synchronized void reserveTable(long clientId, ReservationInfo reservation) {
        int id = reservation.getTableId();
        if(reservedTables.contains(id)){
            return;
        }
        reservedTables.add(id);
        clients.stream()
                .filter(c -> {
                    try {
                        return c.getClientId() != clientId;
                    } catch (RemoteException ex) {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                })
                .forEach(c -> {
                    try {
                        c.notifyTableReserved(id);
                    } catch (RemoteException ex) {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
    }

    @Override
    public synchronized void freeTable(long clientId, int id) {
        reservedTables.removeIf(i -> i == id);
        unavailableTables.removeIf(i -> i == id);
        Predicate<IRmiClient> predicate = c -> {
            try {
                return c.getClientId() != clientId;
            } catch (RemoteException ex) {
                Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        };
        clients.stream()
                .filter(predicate)
                .forEach(c -> {
                    try {
                        c.notifyTableFreed(id);
                    } catch (RemoteException ex) {
                        Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
    }

    private void notifyClientNumberChanged(int noOfClients) {
        clients.forEach(c -> {
            try {
                c.notifyNoClientsChanges(noOfClients);
            } catch (RemoteException ex) {
                Logger.getLogger(RmiServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}

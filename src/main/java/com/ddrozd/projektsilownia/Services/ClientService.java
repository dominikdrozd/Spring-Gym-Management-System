package com.ddrozd.projektsilownia.Services;

import java.util.List;
import java.util.Optional;

import com.ddrozd.projektsilownia.Entities.Address;
import com.ddrozd.projektsilownia.Entities.Client;
import com.ddrozd.projektsilownia.Entities.User;
import com.ddrozd.projektsilownia.Repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;

    public Client registerClient(User user, Address address, String name, String surname, String phone) {
        Client client = new Client();
        client.setName(name);
        client.setSurname(surname);
        client.setPhone(phone);

        client.setUser(user);
        client.setAddress(address);

        clientRepository.save(client);

        return client;
    }

    public Client updateClient(Client client, Address address, String name, String surname, String phone) {
        client.setAddress(address);
        client.setName(name);
        client.setSurname(surname);
        client.setPhone(phone);

        clientRepository.save(client);

        return client;
    }

    public void delete(Client client) {
        clientRepository.deleteById(client.getId());
    }

    public List<Client> findAll() {
        return clientRepository.findAllByOrderByIdAsc();
    }

    public Optional<Client> findById(long id){
        return clientRepository.findById(id);
    }

    public int countWhereAddressId(long addressId) {
        return clientRepository.countByAddressId(addressId);
    }

    public boolean hasValidPass(Client client) {


        return true;
    }

    public Optional<Client> findByUserId(Long id) {
        return clientRepository.findByUserId(id);
    }

}

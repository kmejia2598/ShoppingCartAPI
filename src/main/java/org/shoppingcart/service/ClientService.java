package org.shoppingcart.service;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.component.MemoryDB;
import org.shoppingcart.config.Jwt.JwtService;
import org.shoppingcart.dto.ClientDTO;
import org.shoppingcart.dto.login.AuthResponse;
import org.shoppingcart.dto.login.LoginRequest;
import org.shoppingcart.exception.CustomException;
import org.shoppingcart.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {


    private ClientDTO usuario;
    private final MemoryDB memoryDB;
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    public List<ClientDTO> getClients() {
        ResponseEntity<ClientDTO[]> response = restTemplate.getForEntity("https://fakestoreapi.com/users", ClientDTO[].class);
        ClientDTO[] clients = response.getBody();

        if (clients == null || clients.length == 0) {
            throw new NotFoundException("There are no clients/users available in API");
        }
        return Arrays.asList(clients);
    }

    public Collection<ClientDTO> getAllClientsMemory(){
        return memoryDB.getAllClients();
    }

    public ClientDTO getClientById(Integer id){
        try {
            ResponseEntity<ClientDTO> response = restTemplate.getForEntity("https://fakestoreapi.com/users/" + id, ClientDTO.class);
            ClientDTO client = response.getBody();

            if (client == null) {
                throw new NotFoundException("Producto con ID " + id + " no encontrado");
            }
            return client;
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Producto con ID " + id + " no encontrado");
        }
    }

    public ClientDTO findByNombre(String name){
        return memoryDB.getClientByName(name);
    }

    public ClientDTO getClientByIdMemory(Integer id){
        Optional<ClientDTO> client = memoryDB.getClientById(id);
        if(client.isPresent()){
            return client.get();
        }else {
            throw new NotFoundException("Cliente con ID " + id + " no encontrado");
        }
    }

    public AuthResponse login(LoginRequest request) {
        usuario = findByNombre(request.getUsername());
        //validamos que el usuario exista y retorne
        if (usuario == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "El usuario no existe");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "La contraseña no coincide");
        }

        //autenticacion y retorno de datos del usuario
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = (UserDetails) findByNombre(request.getUsername());
        //generacion del token
        String token = jwtService.getToken(user);
        //retorno de datos
        return AuthResponse.builder()
                .userName(usuario.getUsername())
                .firstname(usuario.getName().getFirstname())
                .lastname(usuario.getName().getLastname())
                .token(token)
                .build();
    }
}

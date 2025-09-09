package org.shoppingcart.service;

import lombok.RequiredArgsConstructor;
import org.shoppingcart.component.MemoryDB;
import org.shoppingcart.config.Jwt.JwtService;
import org.shoppingcart.dto.client.ClientDTO;
import org.shoppingcart.dto.login.AuthResponse;
import org.shoppingcart.dto.login.LoginRequest;
import org.shoppingcart.exception.CustomException;
import org.shoppingcart.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${external.api.fakestore.url}")
    private String urlAPI;

    public List<ClientDTO> getClients() {
        ResponseEntity<ClientDTO[]> response = restTemplate.getForEntity(urlAPI + "/users", ClientDTO[].class);
        ClientDTO[] clients = response.getBody();

        if (clients == null || clients.length == 0) {
            // Throw custom exception if no clients are returned
            throw new NotFoundException("There are no clients/users available in API");
        } else {
            // Iterar sobre cada cliente y encriptar su contraseña
            Arrays.stream(clients).forEach(clientDTO -> {
                String rawPassword = clientDTO.getPassword();
                if (rawPassword != null && !rawPassword.isBlank()) {
                    // Password with its encrypted hash
                    clientDTO.setPassword(passwordEncoder.encode(rawPassword));
                }
            });
        }
        return Arrays.asList(clients);
    }

    public Collection<ClientDTO> getAllClientsMemory() {
        return memoryDB.getAllClients();
    }

    public ClientDTO getClientById(Integer id) {
        try {
            ResponseEntity<ClientDTO> response = restTemplate.getForEntity(urlAPI + "/users/" + id, ClientDTO.class);
            ClientDTO client = response.getBody();

            if (client == null) {
                throw new NotFoundException("Product with ID " + id + " not found");
            } else {
                client.setPassword(passwordEncoder.encode(client.getPassword()));
            }

            return client;
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Product with ID " + id + " not found");
        }
    }

    public ClientDTO findByNombre(String name) {
        return memoryDB.getClientByName(name);
    }

    /**
     * Get user in memory by ID
     * @param id User ID
     * @return response
     */
    public ClientDTO getClientByIdMemory(Integer id) {
        Optional<ClientDTO> client = memoryDB.getClientById(id);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new NotFoundException("Client with ID " + id + " not found");
        }
    }

    public AuthResponse login(LoginRequest request) {
        usuario = findByNombre(request.getUsername());
        //validamos que el usuario exista y retorne
        if (usuario == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "The user does not exist.");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "The password does not match.");
        }

        //authentication and return of user data
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = findByNombre(request.getUsername());
        //token generation
        String token = jwtService.getToken(user);
        //data return
        return AuthResponse.builder()
                .userName(usuario.getUsername())
                .firstname(usuario.getName().getFirstname())
                .lastname(usuario.getName().getLastname())
                .token(token)
                .build();
    }
}

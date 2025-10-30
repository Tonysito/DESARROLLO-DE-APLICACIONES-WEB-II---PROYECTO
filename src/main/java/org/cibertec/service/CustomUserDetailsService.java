package org.cibertec.service;

import java.time.LocalDateTime;

import org.cibertec.dto.CustomUserDetails;
import org.cibertec.entity.Usuario;
import org.cibertec.entity.Usuario.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private org.cibertec.repository.UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado " + username));
		return new CustomUserDetails(usuario);
	}

	@Bean
	CommandLineRunner initUser() {
		return args -> {
			if (userRepository.findByEmail("admin@example.com").isEmpty()) {
				Usuario usuario = new Usuario();
				usuario.setNombre("admin");
				usuario.setApellido("admin");
				usuario.setFechaCreacion(LocalDateTime.now());
				usuario.setEmail("admin@example.com");
				usuario.setPassword(passwordEncoder.encode("secret")); // importante: encriptar
				usuario.setRol(Rol.ADMIN);

				userRepository.save(usuario);
				System.out.println("Usuario inicial creado: admin@example.com / secret");
			}
		};
	}

}

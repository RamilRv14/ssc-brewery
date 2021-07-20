package guru.sfg.brewery.loader;

import guru.sfg.brewery.security.Authority;
import guru.sfg.brewery.security.AuthorityRepository;
import guru.sfg.brewery.security.User;
import guru.sfg.brewery.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class UserDataLoader {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;



    @PostConstruct
    @Transactional
    public void loadData(){


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Authority user = authorityRepository.save(Authority.builder()
                .role("ROLE_USER")
                .build());

        Authority admin = authorityRepository.save(Authority.builder()
                .role("ROLE_ADMIN")
                .build());

        Authority customer = authorityRepository.save(Authority.builder()
                .role("ROLE_CUSTOMER")
                .build());


        userRepository.save(User.builder()
                .username("ramil")
                .password(passwordEncoder.encode("12345"))
                .authority(admin)
                .build());


        userRepository.save(User.builder()
                .username("tagi")
                .password(passwordEncoder.encode("12345"))
                .authority(user)
                .build());

        userRepository.save(User.builder()
                .username("ulvi")
                .password(passwordEncoder.encode("12345"))
                .authority(customer)
                .build());


    }


}

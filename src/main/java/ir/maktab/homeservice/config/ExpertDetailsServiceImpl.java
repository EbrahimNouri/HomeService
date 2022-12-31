package ir.maktab.homeservice.config;

import ir.maktab.homeservice.repository.expert.ExpertRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ExpertDetailsServiceImpl implements UserDetailsService {

    private final ExpertRepository expertRepository;

    public ExpertDetailsServiceImpl(ExpertRepository expertRepository) {
        this.expertRepository = expertRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return expertRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

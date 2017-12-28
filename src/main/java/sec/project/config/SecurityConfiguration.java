package sec.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We are specifically defining a URL to be allowed access by anyone,
        // but IRL it is more likely that we would need to define multiple types
        // of accesses to multiple URLs and make a mistake.
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/supersecreturl").permitAll();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/").permitAll();
        http.authorizeRequests()
                .anyRequest().authenticated().and().formLogin().permitAll();
        
        // Disable CSRF token - hopefully no-one does this without good reason...
        http.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package pkg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration class that requires authentication on all requests except /,
 * and uses the Spring Security authentication provider for Active Directory.
 * It also instantiates a custom user details mapper in order to retrieve extra
 * user attributes from Active Directory.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${ad.domain}")
    private String activeDirectoryDomain;

    @Value("${ad.url}")
    private String activeDirectoryUrl;

    @Value("${ad.base-dn}")
    private String activeDirectoryBaseDN;

    @Override
    protected void configure(HttpSecurity http) throws Exception { //@formatter:off
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin().loginPage("/login").permitAll()
            .and()
                // With Cross-Site Request Forgery (CSRF) protection enabled (which it is by default),
                // log out must use HTTP POST method. Using logoutRequestMatcher to allow log out with any HTTP method.
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll().logoutSuccessUrl("/")
            .and()
                .httpBasic().disable();
    } //@formatter:on

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(activeDirectoryAuthProvider());
    }

    @Bean
    public AuthenticationProvider activeDirectoryAuthProvider() {
        ActiveDirectoryLdapAuthenticationProvider authProvider = new ActiveDirectoryLdapAuthenticationProvider(
                this.activeDirectoryDomain, this.activeDirectoryUrl, this.activeDirectoryBaseDN);

        authProvider.setConvertSubErrorCodesToExceptions(true);
        authProvider.setUseAuthenticationRequestCredentials(true);
        // authProvider.setSearchFilter("(&(objectClass=user)(sAMAccountName={0}))");
        authProvider.setUserDetailsContextMapper(new CustomUserDetailsMapper());
        return authProvider;
    }
    
}

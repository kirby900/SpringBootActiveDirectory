package pkg;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

/**
 * Custom user details class with additional user attributes during
 * authentication process.
 *
 */
public class CustomUserDetails implements LdapUserDetails {
    private static final long serialVersionUID = 1L;
    private LdapUserDetails base;
    private String givenName;
    private String surname;

    public CustomUserDetails(LdapUserDetails base) {
        this.base = base;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return base.getAuthorities();
    }

    @Override
    public String getPassword() {
        return base.getPassword();
    }

    @Override
    public String getUsername() {
        return base.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return base.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return base.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return base.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return base.isEnabled();
    }

    @Override
    public void eraseCredentials() {
        base.eraseCredentials();
    }

    @Override
    public String getDn() {
        return base.getDn();
    }

    public String getGivenName() {
        return givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getFullName() {
        if (this.givenName != null && this.surname != null)
            return this.givenName + " " + this.surname;
        if (this.givenName != null)
            return this.givenName;
        if (this.surname != null)
            return this.surname;
        return null;
    }
}

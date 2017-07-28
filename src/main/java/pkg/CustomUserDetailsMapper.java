package pkg;

import java.util.Collection;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

/**
 * Class to map a few additional name attributes from Active Directory
 * beyond the minimal atttributes included in LdapUserDetailsMapper.
 */
public class CustomUserDetailsMapper extends LdapUserDetailsMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
            Collection<? extends GrantedAuthority> authorities) {
        // Begin with inherited method
        LdapUserDetails ldapUserDetails = (LdapUserDetails) super.mapUserFromContext(ctx, username, authorities);

        // Initialize custom user object from default LDAP user details,
        // then pick up a couple more user attributes from Active Directory.
        CustomUserDetails customUserDetails = new CustomUserDetails(ldapUserDetails);
        customUserDetails.setGivenName(ctx.getStringAttribute("givenName"));
        customUserDetails.setSurname(ctx.getStringAttribute("sn"));
        return customUserDetails;
    }

}

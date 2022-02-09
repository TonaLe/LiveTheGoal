package app.security.Enum;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static app.security.Enum.UserPermission.SUBJECT_READ;
import static app.security.Enum.UserPermission.SUBJECT_WRITE;

public enum UserRole {
    ADMIN(Sets.newHashSet(SUBJECT_READ,SUBJECT_WRITE)),
    USER(Sets.newHashSet(SUBJECT_READ));

    private Set<UserPermission> userPermissions;

    UserRole(Set<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public Set<UserPermission> getUserPermissions(){
        return userPermissions;
    }

    public Set<SimpleGrantedAuthority> getAuthority(){
        Set<SimpleGrantedAuthority> simpleGrantedAuthority = userPermissions.stream()
                .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermission())).collect(Collectors.toSet());
        simpleGrantedAuthority.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return simpleGrantedAuthority;
    }
}
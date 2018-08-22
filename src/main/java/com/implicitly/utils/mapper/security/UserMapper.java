/*
 * ©  Implicitly86 All Rights Reserved
 */

package com.implicitly.utils.mapper.security;

import com.implicitly.domain.security.Role;
import com.implicitly.domain.security.User;
import com.implicitly.dto.security.RoleDTO;
import com.implicitly.dto.security.UserDTO;
import com.implicitly.utils.mapper.EntityMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Реализация {@link EntityMapper} для сущности {@link User}
 *
 * @author Emil Murzakaev.
 */
@Component
public class UserMapper implements EntityMapper<User, UserDTO> {

    /**
     * Маппинг Entity -> Dto.
     *
     * @param source доменная сущность.
     * @return модель передачи данных, соответствующая доменной модели.
     */
    @Override
    public UserDTO toDto(User source) {
        if (source == null) {
            return null;
        }
        UserDTO target = new UserDTO();
        BeanUtils.copyProperties(source, target, "roles");
        List<RoleDTO> roles = source.getRoles().stream()
                .map(it -> {
                    RoleDTO role = new RoleDTO();
                    BeanUtils.copyProperties(it, role);
                    return role;
                })
                .collect(Collectors.toList());
        target.setRoles(roles);
        return target;
    }

    /**
     * Маппинг Dto -> Entity.
     *
     * @param source модель передачи данных, соответствующая доменной модели.
     * @return доменная сущность.
     */
    @Override
    public User toEntity(UserDTO source) {
        if (source == null) {
            return null;
        }
        User target = new User();
        BeanUtils.copyProperties(source, target, "roles");
        List<Role> roles = source.getRoles()
                .stream()
                .map(it -> {
                    Role role = new Role();
                    BeanUtils.copyProperties(it, role);
                    return role;
                })
                .collect(Collectors.toList());
        target.setRoles(roles);
        return target;
    }

}

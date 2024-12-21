package org.aldo.api.service.Implementations;

import lombok.RequiredArgsConstructor;
import org.aldo.api.data.dao.RoleDao;
import org.aldo.api.data.entities.Role;
import org.aldo.api.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleSericeImpl implements RoleService {
    private final RoleDao roleDao;

    @Override
    public void createRole(String name) {
        Role role = new Role();
        role.setType(name);
        roleDao.save(role);
    }
}

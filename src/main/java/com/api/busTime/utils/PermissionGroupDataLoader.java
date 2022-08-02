package com.api.busTime.utils;

import com.api.busTime.model.dao.PermissionDAO;
import com.api.busTime.model.dao.PermissionsGroupDAO;
import com.api.busTime.model.entities.Permission;
import com.api.busTime.model.entities.PermissionsGroup;
import com.api.busTime.model.entities.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//Esse é um seeder q criei para setar ao incializar a aplicação as permissões e grupo de permissões no banco,
//eu não preciso mais do código dentro da função loadPermissionData e loadPermissionGroupData ja que ja foi cadastrado no banco,
//mas deixei para o senhor ver como fiz, futuramente irei fazer uma opção do usuário adicionar uma permissão no frontend
@Component
public class PermissionGroupDataLoader implements CommandLineRunner {

    @Autowired
    private PermissionsGroupDAO permissionsGroupDAO;

    @Autowired
    private PermissionDAO permissionDAO;

    @Override
    public void run(String... args) throws Exception {
        loadPermissionData();
    }

    private void loadPermissionData() {
        if (this.permissionDAO.count() == 0) {

            Permission returnUsers = new Permission(1, "Retornar Usuários", "return_users");
            Permission updatePermissionUser = new Permission(2, "Atualizar Permissão de Usuário", "update_permission_user");
            Permission deleteUser = new Permission(3, "Deletar Usuário", "delete_user");
            Permission createBus = new Permission(4, "Criar Ônibus", "create_bus");
            Permission updateBus = new Permission(5, "Atualizar Ônibus", "update_bus");
            Permission findBusForCurrentUser = new Permission(6, "Retornar Ônibus do Usuário", "return_bus_from_user");
            Permission deleteBus = new Permission(7, "Deletar Ônibus", "delete_bus");

            List<Permission> permissionList = new ArrayList<Permission>() {{
                add(returnUsers);
                add(updatePermissionUser);
                add(deleteUser);
                add(createBus);
                add(updateBus);
                add(findBusForCurrentUser);
                add(deleteBus);
            }};

            this.permissionDAO.saveAll(permissionList);

            loadPermissionGroupData();
        }
    }

    private void loadPermissionGroupData() {
        if (this.permissionsGroupDAO.count() == 0) {
            List<Permission> permissionsDefault = new ArrayList<>();

            List<Permission> permissionsAdm = new ArrayList<Permission>() {{
                add(permissionDAO.findByIdExsits(4));
                add(permissionDAO.findByIdExsits(5));
                add(permissionDAO.findByIdExsits(6));
                add(permissionDAO.findByIdExsits(7));
            }};

            List<Permission> permissionsSuperAdm = new ArrayList<Permission>() {{
                addAll(permissionsAdm);
                add(permissionDAO.findByIdExsits(1));
                add(permissionDAO.findByIdExsits(2));
                add(permissionDAO.findByIdExsits(3));
            }};

            PermissionsGroup permissionsGroupDefault = new PermissionsGroup(1, UserRoles.DEFAULT, permissionsDefault);
            PermissionsGroup permissionsGroupAdm = new PermissionsGroup(2, UserRoles.ADMINISTRATOR, permissionsAdm);
            PermissionsGroup permissionsGroupSuperAdm = new PermissionsGroup(3, UserRoles.SUPER_ADMINISTRATOR, permissionsSuperAdm);
            
            permissionsGroupDAO.save(permissionsGroupDefault);
            permissionsGroupDAO.save(permissionsGroupAdm);
            permissionsGroupDAO.save(permissionsGroupSuperAdm);
        }
    }
}

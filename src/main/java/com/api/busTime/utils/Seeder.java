package com.api.busTime.utils;

import com.api.busTime.model.dao.MenuDAO;
import com.api.busTime.model.dao.PermissionDAO;
import com.api.busTime.model.dao.PermissionsGroupDAO;
import com.api.busTime.model.entities.Menu;
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
public class Seeder implements CommandLineRunner {

    @Autowired
    private PermissionsGroupDAO permissionsGroupDAO;

    @Autowired
    private PermissionDAO permissionDAO;
    
    @Autowired
    private MenuDAO menuDAO;

    @Override
    public void run(String... args) throws Exception {
        loadPermissionData();
    }
    
    private void loadMenusData(){
        if(this.menuDAO.count() == 0){
            Menu profileMenu = new Menu(null, "/profile", "CgProfile", "");
            Menu statisticsMenu = new Menu(null, "/statistics", "VscGraph", );
            Menu logsMenu = new Menu(null, "/logs", "AiOutlineAlignLeft", UserRoles.SUPER_ADMINISTRATOR);
            Menu permissionsMenu = new Menu(null, "/permissions", "AiOutlineLock", UserRoles.SUPER_ADMINISTRATOR);
            Menu favoritesMenu = new Menu(null, "/favorites", "AiOutlineHeart", "");
            Menu homeMenu = new Menu(null, "/home", "BiHomeAlt", "");
            Menu createBusMenu = new Menu(null, "/create-bus", "GiBusStop", UserRoles.ADMINISTRATOR);
            Menu busMenu = new Menu(null, "/bus", "BiBusSchool", UserRoles.ADMINISTRATOR);
            Menu logoutMenu = new Menu(null, "/logout", "BiDoorOpen", "");
        }
    }

    private void loadPermissionData() {
        if (this.permissionDAO.count() == 0) {

            Permission returnUsers = new Permission(1, "Retornar Usuários", PermissionsEnum.RETURN_USERS.getValue());
            Permission updatePermissionUser = new Permission(2, "Atualizar Permissão de Usuário", PermissionsEnum.UPDATE_PERMISSION_USER.getValue());
            Permission deleteUser = new Permission(3, "Deletar Usuário", PermissionsEnum.DELETE_USER.getValue());
            Permission createBus = new Permission(4, "Criar Ônibus", PermissionsEnum.CREATE_BUS.getValue());
            Permission updateBus = new Permission(5, "Atualizar Ônibus", PermissionsEnum.UPDATE_BUS.getValue());
            Permission findBusForCurrentUser = new Permission(6, "Retornar Ônibus do Usuário", PermissionsEnum.RETURN_BUS_FROM_USER.getValue());
            Permission deleteBus = new Permission(7, "Deletar Ônibus", PermissionsEnum.DELETE_BUS.getValue());

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

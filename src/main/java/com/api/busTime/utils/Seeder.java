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

import java.util.AbstractList;
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
        loadMenusData();
        loadPermissionData();
    }

    private void loadMenusData() {
        if (this.menuDAO.count() == 0) {
            Menu profileMenu = new Menu(1L, "/profile", "CgProfile", "Perfil", "");
            Menu statisticsMenu = new Menu(2L, "/statistics", "VscGraph", "Estatísticas", PermissionsEnum.VIEW_STATISTICS.getValue());
            Menu logsMenu = new Menu(3L, "/logs", "AiOutlineAlignLeft", "Logs", PermissionsEnum.VIEW_LOGS.getValue());
            Menu permissionsMenu = new Menu(4L, "/permissions", "AiOutlineLock", "Permissões", PermissionsEnum.UPDATE_PERMISSION_USER.getValue());
            Menu favoritesMenu = new Menu(5L, "/favorites", "AiOutlineHeart", "Favoritos", "");
            Menu homeMenu = new Menu(6L, "/home", "BiHomeAlt", "Home", "");
            Menu createBusMenu = new Menu(7L, "/create-bus", "GiBusStop", "Cadastrar", PermissionsEnum.CREATE_BUS.getValue());
            Menu busMenu = new Menu(8L, "/bus", "BiBusSchool", "Ônibus", PermissionsEnum.CREATE_BUS.getValue());
            Menu logoutMenu = new Menu(9L, "/logout", "BiDoorOpen", "Sair", "");
            
            List<Menu> menuList = new ArrayList<Menu>() {
                {
                    add(profileMenu);
                    add(statisticsMenu);
                    add(logsMenu);
                    add(permissionsMenu);
                    add(favoritesMenu);
                    add(homeMenu);
                    add(createBusMenu);
                    add(busMenu);
                    add(logoutMenu);
                }
            };
            
            menuDAO.saveAll(menuList);
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
            Permission viewLogs = new Permission(8, "Visualizar Logs", PermissionsEnum.VIEW_LOGS.getValue());
            Permission viewStatistics = new Permission(9, "Visualizar Estatísticas", PermissionsEnum.VIEW_STATISTICS.getValue());

            List<Permission> permissionList = new ArrayList<Permission>() {{
                add(returnUsers);
                add(updatePermissionUser);
                add(deleteUser);
                add(createBus);
                add(updateBus);
                add(findBusForCurrentUser);
                add(deleteBus);
                add(viewLogs);
                add(viewStatistics);
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
                add(permissionDAO.findByIdExsits(1));
                add(permissionDAO.findByIdExsits(2));
                add(permissionDAO.findByIdExsits(3));
                addAll(permissionsAdm);
                add(permissionDAO.findByIdExsits(8));
                add(permissionDAO.findByIdExsits(9));
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

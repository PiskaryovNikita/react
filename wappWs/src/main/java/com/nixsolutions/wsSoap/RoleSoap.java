package com.nixsolutions.wsSoap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.nixsolutions.webapp.model.Role;

@WebService
public interface RoleSoap {
	@WebMethod
	void createRole(Role role);

	@WebMethod
	void updateRole(Role role);

	@WebMethod
	void removeRole(Role role);

	@WebMethod
	Role findRoleById(Long roleId);

	@WebMethod
	Role findRoleByName(String name);

	@WebMethod
	List<Role> findAllRoles();
}

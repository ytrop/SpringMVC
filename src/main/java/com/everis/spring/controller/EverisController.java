package com.everis.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.everis.spring.repository.EverisCustomer;
import com.everis.spring.services.EverisCustomerManagementServiceI;

@Controller
@RequestMapping("*")

public class EverisController {
	
@Autowired
private EverisCustomerManagementServiceI customerService;	
	
public static String index = "Index";	
public static String mostrarClientes = "mostrarClientes";	
public static String nuevoCliente = "nuevoCliente";	
public static String buscarPorNombre = "buscarPorNombre";	

@GetMapping("/*")
public String mostrarIndex() {
	
		return index;
	}


@GetMapping("/mostrarClientes")
public String mostrarClientes(Model model) {
	final List<EverisCustomer> customerList = customerService.searchAllCustomers();
	System.out.println(customerList.toString());


	model.addAttribute("customersList", customerList);
	model.addAttribute("btnDropCustomerEnabled", Boolean.FALSE);

	return mostrarClientes;
	
	
	}

@GetMapping("/nuevoCliente")
public String nuevoCliente() {

	return nuevoCliente;
}

@GetMapping("/buscarPorNombre")
public String buscarPorNombre() {

	return buscarPorNombre;
}

@RequestMapping(value = "/añadirClientes", method = RequestMethod.POST)
public String onSubmit(EverisCustomer customer) {
	
	customerService.insertNewCustomer(customer);
	
	return "redirect:/mostrarClientes";
}

@RequestMapping(value = "/buscarClientes", method = RequestMethod.POST)

public String BuscarCliente(Model model, EverisCustomer customer) {
	
	List<EverisCustomer> clientView = new ArrayList<>();
	clientView = customerService.searchByName(customer.getName());
	model.addAttribute("customersList", clientView);
	model.addAttribute("btnDropCustomerEnabled", false);

	return "mostrarClientes";
}

@RequestMapping(value = "/BorrarCliente", method = RequestMethod.POST)
public String BorrarCliente(Model model, @RequestParam("customerId") Integer id) {
	customerService.deleteCustomer(id);

	return "redirect:/mostrarClientes";
}




}

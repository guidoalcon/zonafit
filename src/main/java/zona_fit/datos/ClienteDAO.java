package zona_fit.datos;

import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static zona_fit.conexion.Conexion.getConexion;

public class ClienteDAO implements IClienteDAO{
    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        var sql = "select * from cliente order by id";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }

        }catch (Exception e){
            System.out.println("e = " + e);
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("e = " + e);
            }
        }
        return  clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        var sql = "select * from cliente where id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,cliente.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
            
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("e = " + e);
            }
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        var sql = "insert into cliente(nombre,apellido,membresia) "
                +"values(?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getApellido());
            ps.setInt(3,cliente.getMembresia());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("e = " + e);
            }
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        var sql = "update cliente set nombre=?, apellido=?, membresia=? "
                +"where id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getApellido());
            ps.setInt(3,cliente.getMembresia());
            ps.setInt(4,cliente.getId());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("e = " + e);
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        var sql = "delete from cliente where id = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,cliente.getId());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("e = " + e);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        IClienteDAO clienteDao = new ClienteDAO();
        System.out.println("****LISTAR CLIENTES****");
        var clientes = clienteDao.listarClientes();
        clientes.forEach(System.out::println);

        System.out.println("****BUSCAR CLIENTE****");
        var cliente1 = new Cliente(2);
        System.out.println("cliente1 = " + cliente1);
        var encontrado = clienteDao.buscarClientePorId(cliente1);
        if(encontrado){
            System.out.println("encontrado = " + cliente1);
        }else {
            System.out.println("No se encontro registro: " + cliente1.getId());
        }
        System.out.println("****AGREGAR CLIENTE****");
        var cliente2 = new Cliente("Juan","Perez",104);
        var agregado = clienteDao.agregarCliente(cliente2);
        if(agregado){
            System.out.println("agregado = " + cliente2);
        }else{
            System.out.println("No se agrego al cliente ");
        }
        clientes = clienteDao.listarClientes();
        clientes.forEach(System.out::println);

        System.out.println("****MODIFICAR CLIENTE****");
        var modificarCliente = new Cliente(3,"Carlos","Perez",300);
        var modificado = clienteDao.modificarCliente(modificarCliente);
        if(modificado){
            System.out.println("modificado = " + modificarCliente);
        }else{
            System.out.println("No se modifico al cliente ");
        }
        clientes = clienteDao.listarClientes();
        clientes.forEach(System.out::println);
        System.out.println("****ELIMINAR CLIENTE****");
        var clienteEliminar = new Cliente(6);
        var eliminado = clienteDao.eliminarCliente(clienteEliminar);
        if(eliminado){
            System.out.println("eliminado = " + clienteEliminar);
        }else{
            System.out.println("No se elimino al cliente ");
        }
    }
}

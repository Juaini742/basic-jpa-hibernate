package com.core.basichibernate.service;

import com.core.basichibernate.entity.Role;
import com.core.basichibernate.entity.Users;
import com.core.basichibernate.repository.RoleRepository;
import com.core.basichibernate.repository.UserRepository;
import com.core.basichibernate.validator.UserDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    /**
     * @Autowired UserRepository userRepository : Ini adalah inisialisasi objek UserRepository.
     * UserRepository adalah antarmuka yang digunakan untuk mengakses data pengguna dari database.
     * Dengan menggunakan @Autowired, Spring akan menginisialisasi objek UserRepository secara otomatis.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * @Autowired RoleRepository roleRepository : Ini adalah inisialisasi objek RoleRepository.
     * RoleRepository adalah antarmuka yang digunakan untuk mengakses data peran dari database.
     * Dengan menggunakan @Autowired, Spring akan menginisialisasi objek RoleRepository secara otomatis.
     */
    @Autowired
    RoleRepository roleRepository;

    /**
     * CREATE
     * @param userFormData
     * @return createUser() adalah metode yang digunakan untuk membuat pengguna baru.
     * Metode ini menerima objek UserDTO yang berisi informasi pengguna yang akan dibuat.
     * Metode ini membuat objek Users baru, mengisi informasi pengguna, dan menyimpan pengguna ke dalam database.
     * Jika ada peran yang diberikan dalam objek UserDTO, metode ini juga akan menambahkan peran-peran tersebut ke dalam pengguna.
     * Contoh JSON untuk membuat pengguna baru:
     * <p>
     * {
     * "email": "jane.doe@example.com",
     * "name": "Jane Doe",
     * "password": "123123",
     * "roleNames": ["USER", "ADMIN"]
     * }
     */
    public Users createUser(UserDTO userFormData) {
        Users user = new Users();
        user.setName(userFormData.getName());
        user.setEmail(userFormData.getEmail());
        user.setPassword(userFormData.getPassword());

        if (userFormData.getRoleNames() != null && !userFormData.getRoleNames().isEmpty()) {
            List<Role> roles = roleRepository.findByNameIn(userFormData.getRoleNames());
            user.getRoles().addAll(roles);
        } else {
            Role role = (Role) roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Role 'USER' not found"));
            user.getRoles().add(role);
        }

        return userRepository.save(user);
    }

    /**
     * READ
     * @return getAllUsers adalah sebuah method yang digunakan untuk mengambil semua data pengguna dari database.
     * Method ini menggunakan metode findAll() dari repository UserRepository untuk mengambil semua data pengguna dari database.
     * Kemudian, data pengguna yang diambil akan dikembalikan sebagai objek List<Users>.
     * @return List<Users>
     */
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * @param id
     * @return getUserById adalah sebuah method yang digunakan untuk mengambil data pengguna berdasarkan ID dari database.
     * Method ini menggunakan metode findById(id) dari repository UserRepository untuk mengambil data pengguna berdasarkan ID.
     * Kemudian, data pengguna yang diambil akan dikembalikan sebagai objek Optional<Users>.
     * @return Optional<Users>
     */
    public Map<String, Object> getUserById(Long id, String[] fields) {
        Users user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapUserResponse(user, fields);
    }

    /**
     * READ BY EMAIL
     * @param email
     * @return
     * getByEmail adalah sebuah method yang digunakan untuk mendapatkan data pengguna berdasarkan email.
     * Method ini menggunakan metode findByEmail(email) dari repository UserRepository untuk mendapatkan data pengguna berdasarkan email.
     * Jika data pengguna tidak ditemukan, maka method akan mengembalikan null.
     */
    public Users getByEmail(@NotEmpty(message = "email is required") @Size(min = 2, message = "email must be at least 2 characters") @Email(message = "email is not valid") @UniqueElements(message = "Email already exists") String email) {
        List<Users> user = userRepository.findUniqueEmail(email);
        if (user.isEmpty()) {
            return null;
        }
        return user.get(0);
    }

    /**
     * UPDATE
     * @param id
     * @param userFormData
     * @return updateUser adalah sebuah method yang digunakan untuk memperbarui data pengguna berdasarkan ID.
     * Method ini menggunakan metode findById(id) dari repository UserRepository untuk mengambil data pengguna berdasarkan ID.
     * Kemudian, data pengguna yang diambil akan diperbarui dengan data yang diberikan melalui parameter userFormData.
     * Setelah itu, data pengguna yang telah diperbarui akan disimpan kembali ke database menggunakan metode save(existingUser) dari repository UserRepository.
     * @return Users
     * Contoh JSON untuk update pengguna baru:
     * {
     * "email": "uuse.jane@example.com",
     * "name": "Jane UUS",
     * "password": "123123",
     * "roleNames": ["GUEST"]
     * }
     */
    public Users updateUser(Long id, UserDTO userFormData) {
        Users existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found " + id));
        existingUser.setName(userFormData.getName());
        existingUser.setEmail(userFormData.getEmail());

        if (userFormData.getRoleNames() != null && !userFormData.getRoleNames().isEmpty()) {
            List<Role> newRole = roleRepository.findByNameIn(userFormData.getRoleNames());
            existingUser.setRoles(new HashSet<>(newRole));
        }

        return userRepository.save(existingUser);
    }

    /**
     * DELETE
     * @param id deleteUser adalah sebuah method yang digunakan untuk menghapus data pengguna berdasarkan ID.
     *           Method ini menggunakan metode deleteById(id) dari repository UserRepository untuk menghapus data pengguna berdasarkan ID.
     * @return void
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    /**
     * CUSTOM RESPONSE FOR USERS
     * @param users
     * @param fields
     * @return mapUserResponse adalah sebuah method yang digunakan untuk memetakan objek Users menjadi objek Map<String, Object>.
     * Method ini menggunakan parameter fields untuk menentukan field-field yang akan dimasukkan ke dalam objek Map.
     * Jika parameter fields kosong, maka semua field dari objek Users akan dimasukkan ke dalam objek Map.
     */
    private List<Map<String, Object>> mapUsersResponse(List<Users> users, String[] fields) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Users u : users) {
            result.add(mapUserResponse(u, fields));
        }
        return result;
    }

    /**
     * CUSTOM RESPONSE FORM USER
     * @param u
     * @param fields
     * @return mapUserResponse adalah sebuah method yang digunakan untuk memetakan objek Users menjadi objek Map<String, Object>.
     * Method ini menggunakan parameter fields untuk menentukan field-field yang akan dimasukkan ke dalam objek Map.
     * Jika parameter fields kosong, maka semua field dari objek Users akan dimasukkan ke dalam objek Map.
     */
    private Map<String, Object> mapUserResponse(Users u, String[] fields) {
        Map<String, Object> response = new HashMap<>();
        if (fields == null || fields.length == 0) {
            response.put("id", u.getId());
            response.put("name", u.getName());
            response.put("email", u.getEmail());
            response.put("password", u.getPassword());
            response.put("profile", u.getProfile());
            response.put("comments", u.getComments());
            response.put("posts", u.getPosts());
            response.put("roles", u.getRoles());
        } else {
            for (String field : fields) {
                switch (field) {
                    case "id":
                        response.put("id", u.getId());
                        break;
                    case "name":
                        response.put("name", u.getName());
                        break;
                    case "email":
                        response.put("email", u.getEmail());
                        break;
                    case "password":
                        response.put("password", u.getPassword());
                        break;
                    case "profile":
                        response.put("profile", u.getProfile());
                        break;
                    case "comments":
                        response.put("comments", u.getComments());
                        break;
                    case "posts":
                        response.put("posts", u.getPosts());
                        break;
                    case "roles":
                        response.put("roles", u.getRoles());
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid field: " + field);
                }
            }
        }
        return response;
    }

}

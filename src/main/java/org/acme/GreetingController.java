package org.acme;

import io.quarkus.hibernate.orm.panache.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users-page")
public class GreetingController {
    @GetMapping
    public String hello() {
        return "Hello Spring";
    }

    @GetMapping("/users")
    public List<AppUser> getUsers() {
        return AppUser.listAll();
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody AppUser user) {
        user.id = null;
        user.persist();
        return ResponseEntity.status(204).build();
    }

    @PostMapping
    public ResponseEntity<List<AppUser>> findUsers(@RequestBody AppUserFilter req) {
        var queryParams = new HashMap<String, Object>();
        var queryEntities = new ArrayList<String>();

        if (Objects.equals(req.nameFilterType, "~")) {
            queryParams.put("name", req.name);
            queryEntities.add("name like %:name");
        }

        if (Objects.equals(req.nameFilterType, ">")) {
            queryParams.put("name", req.name);
            queryEntities.add("name like :name%");
        }

        if (Objects.equals(req.nameFilterType, "<")) {
            queryParams.put("name", req.name);
            queryEntities.add("name like %:name");
        }

        if (Objects.equals(req.surnameFilterName, "~")) {
            queryParams.put("surname", req.surname);
            queryEntities.add("surname like %:surname%");
        }

        var q = joinQuery(queryEntities);

        List<AppUser> list;

        PanacheQuery<PanacheEntityBase> panacheEntityBasePanacheQuery;
        if (!Objects.equals(q, "")) {
            panacheEntityBasePanacheQuery = AppUser.find(q, queryParams);
        } else {
            panacheEntityBasePanacheQuery = AppUser.findAll();
        }
        list = panacheEntityBasePanacheQuery.list();

        return ResponseEntity.ok(list);
    }

    String joinQuery(List<String> queryStrings) {
        String res = "";
        for (var s : queryStrings) {
            if (res.equals("")) {
                res += s;
                continue;
            }

            res += " and" + s;
        }
        return res;
    }

}

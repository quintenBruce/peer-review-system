package com.example.PaperReview.repositories;

import com.example.PaperReview.models.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrganizationRepository {
    private static final List<Organization> organizations = new ArrayList<>();
    private static int nextId = 1;

    public static void save(Organization organization) {
        organization.setId(nextId++);
        organizations.add(organization);
    }

    public static Organization findById(int id) {
        for (Organization org : organizations) {
            if (org.getId() == id) {
                return org;
            }
        }
        return null; // or throw an exception if not found
    }

    public static Organization findByName(String name) {
        Optional<Organization> optionalOrganization = organizations.stream()
                .filter(org -> org.getName().equals(name))
                .findFirst();
        return optionalOrganization.orElse(null);
    }

    public static List<Organization> findAll() {
        return new ArrayList<>(organizations);
    }

    public static void update(Organization organization) {
        for (Organization org : organizations) {
            if (org.getId() == organization.getId()) {
                org.setName(organization.getName());
                org.setDescription(organization.getDescription());
                break;
            }
        }
    }

    public static void delete(int id) {
        organizations.removeIf(org -> org.getId() == id);
    }
}

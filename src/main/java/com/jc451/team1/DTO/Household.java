package DTO;

import java.util.List;
import java.util.Objects;

public class Household {
    private int householdId;
    private String householdName;
    private String address;
    private List<User> users;

    public int getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(int householdId) {
        this.householdId = householdId;
    }

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Household household = (Household) o;
        return householdId == household.householdId && Objects.equals(householdName, household.householdName) && Objects.equals(address, household.address) && Objects.equals(users, household.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(householdId, householdName, address, users);
    }

    @Override
    public String toString() {
        return "Household{" +
                "householdId=" + householdId +
                ", householdName='" + householdName + '\'' +
                ", address='" + address + '\'' +
                ", users=" + users +
                '}';
    }

}

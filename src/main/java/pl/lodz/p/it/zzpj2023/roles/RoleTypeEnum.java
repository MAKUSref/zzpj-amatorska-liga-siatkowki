package pl.lodz.p.it.zzpj2023.roles;

public enum RoleTypeEnum {
    ROLE_ADMIN (Roles.ADMIN),
    ROLE_CLIENT (Roles.MANAGER),
    ROLE_MANAGER (Roles.REFEREE),
    ROLE_COACH (Roles.COACH),
    ROLE_CAPITAN (Roles.CAPITAN);

    public final String name;

    private RoleTypeEnum(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}

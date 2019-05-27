package crm.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum IdentityProvider {
  PASSWORD (1, "PASSWORD"),
  FACEBOOK(2, "FACEBOOK"),
  OPENID(3, "OPENID"),
  PHONE(4, "PHONE");

  static final Map<Integer, IdentityProvider> map = new HashMap<>();

  static {
    for (IdentityProvider c : IdentityProvider.values())
      map.put(c.getId(), c);
  }

  private int id;
  private String name;

  IdentityProvider(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static Optional<IdentityProvider> findByNameIgnoreCase(String name) {
    return map.values().stream()
        .filter(provider -> provider.getName().equalsIgnoreCase(name)).findAny();
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}



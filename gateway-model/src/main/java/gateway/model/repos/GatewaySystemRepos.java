package gateway.model.repos;

import gateway.model.entity.GatewayClient;
import gateway.model.entity.GatewaySystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewaySystemRepos extends JpaRepository<GatewaySystem,Long> {
}

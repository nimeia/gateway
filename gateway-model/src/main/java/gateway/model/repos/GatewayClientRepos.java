package gateway.model.repos;

import gateway.model.entity.GatewayClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayClientRepos extends JpaRepository<GatewayClient,Long> {
}

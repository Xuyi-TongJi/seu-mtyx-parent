package edu.seu.mtyx.search.repository;

import edu.seu.mtyx.model.search.SkuEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SkuRepository extends ElasticsearchRepository<SkuEs, Long> {

}

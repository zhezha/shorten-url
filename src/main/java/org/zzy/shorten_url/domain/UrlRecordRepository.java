package org.zzy.shorten_url.domain;


import org.springframework.data.repository.CrudRepository;

public interface UrlRecordRepository extends CrudRepository<UrlRecord, Long> {

}

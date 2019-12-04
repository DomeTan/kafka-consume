package cn.waner.kafkaconsume.common.config;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * 角色模型。
 *
 * @author TanChong
 * create date 2019\12\4 0004
 */
@Document(indexName = "role",type = "role",shards = 1,replicas = 2)
public class Role {

    @Id
    private Long id;

    private String name;

    private String description;

    private List<Authority> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }


}

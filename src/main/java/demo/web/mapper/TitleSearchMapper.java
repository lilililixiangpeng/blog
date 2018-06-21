package demo.web.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface TitleSearchMapper {
    public String searchtitle(@Param("name")String name);
}

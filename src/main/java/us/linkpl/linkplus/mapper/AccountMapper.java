package us.linkpl.linkplus.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import org.springframework.stereotype.Repository;
import us.linkpl.linkplus.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author samsara
 * @since 2021-06-07
 */
@Repository
public interface AccountMapper extends BaseMapper<Account> {

    //根据自增ID随机获取用户
    @Select("SELECT * FROM account WHERE id >= ((SELECT MAX(id) FROM account)-(SELECT MIN(id) FROM account)) * RAND() + (SELECT MIN(id) FROM account)  LIMIT #{num}")
    List<Account> selectRandomAccount(@Param("num") Integer num);
}

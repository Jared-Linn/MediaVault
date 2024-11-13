package com.linqiumeng.mediavault.mapper;

import com.linqiumeng.mediavault.vo.Page;
import com.linqiumeng.mediavault.vo.UserQueryParams;
import org.apache.ibatis.annotations.*;
import com.linqiumeng.mediavault.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Mapper
public interface UserMapper {
    // SELECT * FROM user;
    @Select("SELECT * FROM user;")
    List<User> findAll();

    @Select("SELECT * FROM user WHERE username = #{name}")
    User findByName(String name);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Integer id);

    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User findByPhone(String phone);

    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User findByNameAndPassword(String username, String password);

    @Select("SELECT * FROM user WHERE phone = #{phone} AND password = #{password}")
    User findByPhoneAndPassword(String phone, String password);


    // INSERT
    @Update("INSERT INTO `user` (`username`, `password`, `phone`) VALUES (#{username}, #{password},#{phone});")
    @Transactional
    void save(User user);


    @Update("<script>UPDATE `user`" +
        "<set>" +
        "<if test='name != null and name != \"\"'>`username` = #{username},</if>" +
        "<if test='password != null and password != \"\"'>`password` = #{password},</if>" +
        "<if test='phone != null and phone != \"\"'>`phone` = #{phone},</if>" +
        "</set>" +
        "WHERE `id` = #{id}" +
        "</script>")
    @Transactional
    int updateById(User user);


    @Delete("DELETE FROM `user` WHERE `id` = #{id}")
    int deleteById(Integer id);

    /**
     *分页查询 - 根据分页查询的数据 进行 模糊查询
     *  @Param("params") UserQueryParams params：这是一个 UserQueryParams 对象，包含了多个查询条件。
     * @Param("offset") int offset：分页查询的偏移量。
     * @Param("limit") int limit：每页记录数。
     */
    @Select("<script>" +
            "SELECT * FROM `user` " +
            "<where>" +
            "<if test='params.username != null and params.username != \"\"'>AND username LIKE CONCAT('%', #{params.username}, '%')</if>" +
            "<if test='params.id != null and params.id != \"\"'>AND id LIKE CONCAT('%', #{params.id}, '%')</if>" +
            "<if test='params.phone != null and params.phone != \"\"'>AND phone LIKE CONCAT('%', #{params.phone}, '%')</if>" +
            "</where>" +
            "LIMIT #{offset}, #{limit}" +
            "</script>")
    List<User> findByPageAndConditions(@Param("params") UserQueryParams params, @Param("offset") int offset, @Param("limit") int limit);


//    @Select("SELECT count(*) FROM `user`")

    @Select("<script>" +
            "SELECT COUNT(*) FROM `user` " +
            "<where>" +
            "<if test='username != null and username != \"\"'>AND username LIKE CONCAT('%', #{username}, '%')</if>" +
            "<if test='id != null and id != \"\"'>AND id LIKE CONCAT('%', #{id}, '%')</if>" +
            "<if test='phone != null and phone != \"\"'>AND phone LIKE CONCAT('%', #{phone}, '%')</if>" +
            "</where>" +
            "</script>")
    int getTotalUserCountByConditions(UserQueryParams params);
}

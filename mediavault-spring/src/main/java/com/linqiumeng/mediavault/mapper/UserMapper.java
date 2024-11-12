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

    @Select("SELECT * FROM user WHERE name = #{name}")
    User findByName(String name);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Integer id);

    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User findByPhone(String phone);

    @Select("SELECT * FROM user WHERE name = #{name} AND password = #{password}")
    User findByNameAndPassword(String name, String password);

    @Select("SELECT * FROM user WHERE phone = #{phone} AND password = #{password}")
    User findByPhoneAndPassword(String phone, String password);


    // INSERT
    @Update("INSERT INTO `user` (`name`, `password`, `phone`) VALUES (#{name}, #{password},#{phone});")
    @Transactional
    void save(User user);
    @Update("<script>UPDATE `user`" +
        "<set>" +
        "<if test='name != null and name != \"\"'>`name` = #{name},</if>" +
        "<if test='password != null and password != \"\"'>`password` = #{password},</if>" +
        "<if test='phone != null and phone != \"\"'>`phone` = #{phone},</if>" +
        "</set>" +
        "WHERE `id` = #{id}" +
        "</script>")
    @Transactional
    void updateById(User user);


    @Delete("DELETE FROM `user` WHERE `id` = #{id}")
    void deleteById(Integer id);

    /**
     *分页查询 - 根据分页查询的数据 进行 模糊查询
     *  @Param("params") UserQueryParams params：这是一个 UserQueryParams 对象，包含了多个查询条件。
     * @Param("offset") int offset：分页查询的偏移量。
     * @Param("limit") int limit：每页记录数。
     */

    @Select("<script>" +
            "SELECT * FROM `user` " +
            "<where>" +
            "<if test='params.name != null and params.name != \"\"'>AND name LIKE CONCAT('%', #{params.name}, '%')</if>" +
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
            "<if test='name != null and name != \"\"'>AND name LIKE CONCAT('%', #{name}, '%')</if>" +
            "<if test='id != null and id != \"\"'>AND id LIKE CONCAT('%', #{id}, '%')</if>" +
            "<if test='phone != null and phone != \"\"'>AND phone LIKE CONCAT('%', #{phone}, '%')</if>" +
            "</where>" +
            "</script>")
    int getTotalUserCountByConditions(UserQueryParams params);
}

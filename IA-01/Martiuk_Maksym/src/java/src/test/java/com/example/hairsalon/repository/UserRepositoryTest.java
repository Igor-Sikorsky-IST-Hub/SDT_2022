package com.example.hairsalon.repository;

import com.example.hairsalon.container.IntegrationTestBase;
import com.example.hairsalon.entity.Role;
import com.example.hairsalon.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Sql(
        value = {"/user-profile-test-data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class UserRepositoryTest extends IntegrationTestBase {

    private static final String CORRECT_USERNAME = "new unique username";
    @Autowired
    private UserRepository userRepository;

    @Test
    void injectComponentNotNull() {
        assertThat(userRepository, notNullValue());
    }

    @Test
    void findUserByIdSuccessfulTest() {
        var user = userRepository.findById(10).orElse(null);

        assertThat(user, notNullValue());
        assertThat(user.getRoles(), hasSize(2));
        assertThat(user.getProfileId(), is(1));
    }

    @Test
    void findUserByIdWithNotExistingIdTest() {
        var user = userRepository.findById(1000).orElse(null);

        assertThat(user, nullValue());
    }

    @Test
    void findUserByIdIfIdIsNullTest() {
        var user = userRepository.findById(null).orElse(null);

        assertThat(user, nullValue());
    }

    @Test
    void findByUsernameSuccessfulTest() {
        var user = userRepository.findByUsername("ddevon").orElse(null);

        assertThat(user, notNullValue());
        assertThat(user.getProfileId(), is(11));
        assertThat(user.getRoles(), containsInAnyOrder(Role.CLIENT, Role.BARBER));
    }

    @Test
    void findByNotExistingUsernameTest() {
        var user = userRepository.findByUsername("not existing").orElse(null);

        assertThat(user, nullValue());
    }

    @Test
    void findByUsernameIfNullValuePassedTest() {
        var user = userRepository.findByUsername(null).orElse(null);

        assertThat(user, nullValue());
    }

    @Test
    void findAllUsersSizeTest() {
        List<User> users = userRepository.findAll();

        System.out.println(users);

        assertThat(users, hasSize(12));
    }

    @Test
    void findAllUsersProfileIdNotNullTest() {
        var users = userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getId))
                .toList();

        assertThat(users.get(0).getProfileId(), nullValue());
        assertThat(users.get(10).getProfileId(), is(10));
    }

    @Test
    void findAllUsersRolesJoinedTest() {
        var users = userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getId))
                .toList();

        assertThat(users.get(11).getRoles(), containsInAnyOrder(Role.CLIENT, Role.BARBER));
    }

    @Test
    void saveUserSuccessfulTest() {
        var user = getCorrectUser();

        var saved = userRepository.save(user);

        var findUser = userRepository.findById(saved.getId()).orElse(null);

        assertThat(findUser, notNullValue());
        assertThat(findUser.getUsername(), is(CORRECT_USERNAME));
        assertThat(findUser.getRoles(), containsInAnyOrder(Role.CLIENT));
    }

    @Test
    void saveUserWithGivenZeroIdTest() {
        var user = getCorrectUser();
        user.setId(0);
        var saved = userRepository.save(user);

        var findUser = userRepository.findById(saved.getId()).orElse(null);

        assertThat(findUser, notNullValue());
        assertThat(findUser.getId(), is(21));
    }

    @Test
    void saveUserWithGivenNullIdTest() {
        var user = getCorrectUser();
        user.setId(null);
        var saved = userRepository.save(user);

        var findUser = userRepository.findById(saved.getId()).orElse(null);

        assertThat(findUser, notNullValue());
        assertThat(findUser.getId(), is(21));
        assertThat(findUser.getProfileId(), nullValue());
    }

    @Test
    void saveUserWithNullUsernameTest() {
        var user = getCorrectUser();
        user.setUsername(null);

        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
    }

    @Test
    void updateUserSuccessfulTest() {
        var user = getCorrectUser();
        user.setId(20);
        userRepository.update(user);

        var find = userRepository.findById(20).orElse(null);

        assertThat(find, notNullValue());
        assertThat(find.getProfileId(), is(11));
        assertThat(find.getUsername(), is(CORRECT_USERNAME));
    }

    @Test
    void existUserByIdTest() {

    }

    @Test
    void existUserByIdWithNotExistingIdTest() {

    }

    private User getCorrectUser() {
        return User.builder()
                .active(true)
                .username(CORRECT_USERNAME)
                .password("1")
                .roles(Set.of(Role.CLIENT))
                .build();
    }

}

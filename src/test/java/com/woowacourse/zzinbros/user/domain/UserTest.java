package com.woowacourse.zzinbros.user.domain;

import com.woowacourse.zzinbros.user.exception.IllegalUserArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class UserTest extends UserBaseTest {
    public static final String BASE_NAME = "test";
    public static final String BASE_EMAIL = "test@example.com";
    public static final String BASE_PASSWORD = "12345678";

    private User sender;

    private User receiver;

    @BeforeEach
    public void setUp() {
        sender = new User(BASE_NAME, BASE_EMAIL, BASE_PASSWORD);
        receiver = userSampleOf(SAMPLE_TWO);
    }

    @Test
    @DisplayName("유저 이름이 제한길이를 초과했을때 예외를 던진다")
    public void userNameOverMaxLength() {
        final String invalidName = "열자이상의이름열자이상";
        assertThatThrownBy(() ->
                new User(invalidName, BASE_EMAIL, BASE_PASSWORD)).isInstanceOf(IllegalUserArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호의 길이가 제한 길이 미만일때 예외를 던진다")
    public void userPasswordUnderMinLength() {
        final String invalidPassword = "1aA!";
        assertThatThrownBy(() ->
                new User(BASE_NAME, BASE_EMAIL, invalidPassword)).isInstanceOf(IllegalUserArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호의 길이가 제한 길이 미만일때 예외를 던진다")
    public void userPasswordOverMaxLength() {
        final String invalidPassword = "1aA!aaaaaaaaaaaaaaaaaaaaaaaaaaa";
        assertThatThrownBy(() ->
                new User(BASE_NAME, BASE_EMAIL, invalidPassword)).isInstanceOf(IllegalUserArgumentException.class);
    }

    @Test
    @DisplayName("이메일 형식이 아닐때 예외를 던진다")
    public void invalidUserEmail() {
        final String invalidEmail = "test";
        assertThatThrownBy(() ->
                new User(BASE_NAME, invalidEmail, BASE_PASSWORD)).isInstanceOf(IllegalUserArgumentException.class);
    }

    @Test
    @DisplayName("유저를 업데이트한다")
    public void update() {
        final String updatedName = "새 이름";
        final String updatedPassword = "newPassword!@";
        final String updatedEmail = "updated@test.com";

        User updatedUser = new User(updatedName, updatedEmail, updatedPassword);
        sender.update(updatedUser);

        assertThat(sender.getName()).isEqualTo(updatedName);
        assertThat(sender.getEmail()).isEqualTo(updatedEmail);
        assertThat(sender.getPassword()).isEqualTo(updatedPassword);
    }

    @Test
    @DisplayName("비밀번호 체크")
    public void matchPassword() {
        assertTrue(sender.matchPassword(BASE_PASSWORD));
    }

    @Test
    @DisplayName("유저가 다른 유저한테 친구 요청을 한다")
    public void requestFriend() {
        assertThat(receiver.receiveFriendRequest(sender)).isTrue();
    }

    @Test
    @DisplayName("유저가 다른 유저한테 친구 요청을 실패한다")
    public void requestFriendFail() {
        assertThat(receiver.receiveFriendRequest(sender)).isTrue();
        assertThat(receiver.receiveFriendRequest(sender)).isFalse();
    }

    @Test
    @DisplayName("서로 친구 요청을 했을 때 친구 관계가 된다")
    public void connectFriend() {
        receiver.receiveFriendRequest(sender);
        sender.receiveFriendRequest(receiver);

        assertThat(sender.isFriendWith(receiver)).isTrue();
        assertThat(receiver.isFriendWith(sender)).isTrue();
    }

    @Test
    @DisplayName("서로 친구 요청을 하지 않았을 때 친구 관계가 되지 않는다")
    public void connectFriendFail() {
        receiver.receiveFriendRequest(sender);

        assertThat(sender.isFriendWith(receiver)).isFalse();
        assertThat(receiver.isFriendWith(sender)).isFalse();
    }
}
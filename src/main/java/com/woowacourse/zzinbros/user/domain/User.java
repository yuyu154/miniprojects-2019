package com.woowacourse.zzinbros.user.domain;

import com.woowacourse.zzinbros.common.domain.BaseEntity;
import com.woowacourse.zzinbros.mediafile.domain.MediaFile;
import com.woowacourse.zzinbros.user.exception.IllegalUserArgumentException;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class User extends BaseEntity {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 10;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 30;
    private static final String EMAIL_PATTERN = "^.+@.+$";

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = MAX_PASSWORD_LENGTH)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_file_id", foreignKey = @ForeignKey(name = "fk_user_to_media_file"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MediaFile profile;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.PERSIST)
    private Set<FriendRequest> friendRequests = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private Set<Friend> friends = new HashSet<>();

    @Transient
    private Set<User> realFriendRequests = new HashSet<>();

    @Transient
    private Set<User> realFriends = new HashSet<>();

    public User() {
    }

    public User(String name, @Email String email, String password) {
        this(name, email, password, new MediaFile("/images/default/eastjun_profile.jpg"));
    }

    public User(String name, @Email String email, String password, MediaFile profile) {
        validateLength(name, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
        validateLength(password, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        validatePattern(email, EMAIL_PATTERN);
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    private void validateLength(String name, int minNameLength, int maxNameLength) {
        if (!matchLength(name, minNameLength, maxNameLength)) {
            String message = String.format("길이가 %d 이상 %d 미만이어야 합니다", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
            throw new IllegalUserArgumentException(message);
        }
    }

    private void validatePattern(String element, String pattern) {
        if (!matchRegex(element, pattern)) {
            throw new IllegalUserArgumentException();
        }
    }

    private boolean matchLength(String input, int min, int max) {
        return (input.length() >= min && input.length() < max);
    }

    private boolean matchRegex(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public void update(User updatedUser) {
        this.name = updatedUser.name;
        this.email = updatedUser.email;
        this.password = updatedUser.password;
        this.profile = updatedUser.profile;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isAuthor(User another) {
        return this.email.equals(another.email)
                && this.password.equals(another.password);
    }

    public boolean receiveFriendRequest(User sender) {
        // 친구 요청과 친구 추가를 어떻게 분리해야 하나...
        if (!hasFriendRequest(sender)) {
            realFriendRequests.add(sender);

            friendRequests.add(FriendRequest.create(sender, this));

            makeFriend(sender);
            return true;
        }
        return false;
    }

    private boolean hasFriendRequest(User sender) {
        return realFriendRequests.contains(sender);
    }

    private void makeFriend(User sender) {
        // 서로 친구 추가
        if (canFriend(sender)) {
            addFriend(sender);
            sender.addFriend(this);
            this.friends.add(Friend.create(this, sender));
            sender.friends.add(Friend.create(sender, this));
        }
    }

    private boolean canFriend(User sender) {
        return hasFriendRequest(sender) && sender.hasFriendRequest(this);
    }

    private void addFriend(User sender) {
        this.realFriends.add(sender);
    }

    public boolean isFriendWith(User friend) {
        return realFriends.contains(friend);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public MediaFile getProfile() {
        return profile;
    }
}

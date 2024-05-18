package hackathon.aboba.backend_aboba.service;

import hackathon.aboba.backend_aboba.exception.ServerExceptions;
import hackathon.aboba.backend_aboba.model.User;
import hackathon.aboba.backend_aboba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void updateAccessToken(User user, String accessToken) {
        user.setAccessToken(accessToken);
        userRepository.save(user);
    }

    public void updateRefreshToken(User user, String refreshToken) {
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    public User findUserOrThrow(String username) {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            ServerExceptions.USER_NOT_FOUND.throwException();
        }
        return user;
    }

    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User createOrUpdateUser(User user) {
        return userRepository.save(user);
    }
}

package capweb.capprac;

import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 중복 회원 체크 - Check if the user already exists
    public boolean isUserExists(String usId) {
        List<User> users = userRepository.findUserById(usId);
        return !users.isEmpty(); // 사용자 리스트가 비어있지 않으면 사용자가 이미 존재함
    }

    //회원가입-0529여기까지
    @Transactional
    public User registerUser(String usId, String usPw, String usName, String usJoinIP) {
        // 필수값 체크
        if (usId == null || usId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        if (usPw == null || usPw.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (usName == null || usName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        if (usJoinIP == null || usJoinIP.trim().isEmpty()) {
            throw new IllegalArgumentException("Join IP cannot be null or empty.");
        }
        if (!isUserExists(usId)) {
            User newUser = new User();
            newUser.setUsId(usId);
            newUser.setUsPw(usPw);
            newUser.setUsName(usName);
            newUser.setUsJoindate(new Date());
            newUser.setUsJoinIP(usJoinIP);
            userRepository.save(newUser);
            return newUser;
        } else {
            // 중복된 사용자 ID로 인한 회원가입 실패 처리
            throw new IllegalStateException("이미 존재하는 사용자 ID입니다.");
        }
    }


    // 사용자 정보 업데이트 - Update user information (except usIndex, usId, usJoindate, usJoinIP)
    @Transactional
    public boolean updateUser(int usIndex, String usPw, String usName, String usFixIP) {
        if(usIndex<=0||usPw==null||usPw.trim().isEmpty()||usName==null||usName.trim().isEmpty()||usFixIP==null||usFixIP.trim().isEmpty()){
            return false;
        }
        User user = userRepository.findUserByIndex(usIndex);
        if (user != null) {
            // 인덱스, 아이디, 가입 날짜, 가입 IP는 수정하지 않습니다.
            user.setUsPw(usPw); // 비밀번호 업데이트
            user.setUsName(usName); // 이름 업데이트
            user.setUsFixdate(new Date()); // 수정 날짜를 현재 날짜로 업데이트
            user.setUsFixIP(usFixIP); // 수정 IP 업데이트
            userRepository.update(user); // 변경 사항 저장
            return true;
        }
        return false;
    }


    // 전체회원조회 - 모든 사용자를 조회하는 메소드입니다.
    public List<User> getAllUsers() {
        return userRepository.findAllUsers(); // UserRepository를 통해 모든 사용자 조회
    }

    // 로그인 - 사용자를 로그인하는 메소드입니다.
    public User loginUser(String usId, String usPw) {
        try {
            List<User> users = userRepository.findUserById(usId); // ID로 사용자 조회
            if (users.get(0).getUsPw().equals(usPw)) {
                // 비밀번호가 일치하면 사용자 인증 성공
                return users.get(0);
            }
        } catch (NoResultException e) {
            // 사용자를 찾을 수 없거나 비밀번호가 일치하지 않음
        }
        return null; // 로그인 실패
    }

    // 로그아웃 - 사용자를 로그아웃하는 메소드입니다. (일반적으로 세션 관리를 통해 처리됩니다)
    public void logoutUser(User user) {
        // 세션을 무효화하거나 사용자 세부 정보를 컨텍스트에서 제거
    }

    // 삭제 - ID로 사용자를 삭제하는 메소드입니다.
    @Transactional
    public void deleteUserById(String usId) {
        try {
            List<User> users = userRepository.findUserById(usId); // ID로 사용자 조회
            userRepository.deleteByIndex(users.get(0).getUsIndex()); // UserRepository를 통해 사용자 삭제
        } catch (NoResultException e) {
            // 사용자를 찾을 수 없음
        }
    }
}

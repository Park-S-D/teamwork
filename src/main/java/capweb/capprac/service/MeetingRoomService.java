package capweb.capprac.service;

import capweb.capprac.entity.MeetingRoom;
import capweb.capprac.repository.MeetingRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class MeetingRoomService {

   @Autowired
   private MeetingRoomRepository meetingRoomRepository;

   @Transactional
   //만들기- 모임방과 모임방,모임방이름,카테고리을 필수로 입력받고 유니크 필드는 중복체크해서 만들기
    public MeetingRoom createMeetingRoom(String mrMrid, String mrName, String mrCategory) {
        // 필수값 체크
        if (mrMrid == null || mrMrid.trim().isEmpty()) {
            throw new IllegalArgumentException("Meeting room ID cannot be null or empty.");
        }
        if (mrName == null || mrName.trim().isEmpty()) {
            throw new IllegalArgumentException("Meeting room name cannot be null or empty.");
        }
        if (mrCategory == null || mrCategory.trim().isEmpty()) {
            throw new IllegalArgumentException("Meeting room category cannot be null or empty.");
        }

        // 중복 체크
        if (!meetingRoomRepository.findMeetingRoomByMrid(mrMrid).isEmpty() ||
                !meetingRoomRepository.findMeetingRoomByName(mrName).isEmpty()) {
            throw new IllegalStateException("A meeting room with the same ID or name already exists.");
        }

        // 새로운 MeetingRoom 객체 생성 및 저장
        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setMrMrid(mrMrid);
        meetingRoom.setMrName(mrName);
        meetingRoom.setMrCategory(mrCategory);
        meetingRoomRepository.save(meetingRoom);
        return meetingRoom;
    }


    // 삭제 - mrIndex로 MeetingRoom 삭제
    @Transactional
    public boolean deleteMeetingRoom(int mrIndex) {
        MeetingRoom meetingRoom = meetingRoomRepository.findMeetingRoomByIndex(mrIndex);
        if (meetingRoom != null) {
            meetingRoomRepository.deleteByIndex(mrIndex);
            return true; // 삭제 성공
        }
        return false; // 회의실이 없으면 삭제 실패
    }

    // 수정 - mrName과 mrCategory만 수정 가능
    //인덱스,모임방이름,카테고리를 필수로 입력받게하고 유니크필드는 중복체크하고나서 수정하기
    @Transactional
    public boolean updateMeetingRoom(int mrIndex, String newMrName, String newMrCategory) {
        if(mrIndex <= 0 || newMrName == null || newMrName.trim().isEmpty() || newMrCategory == null || newMrCategory.trim().isEmpty()) {
            return false;
        }
        MeetingRoom meetingRoom = meetingRoomRepository.findMeetingRoomByIndex(mrIndex);
        if (meetingRoom == null) {
            return false; // 회의실이 없으면 수정 실패
        }
        // 이름 중복 체크
        List<MeetingRoom> existingMeetingRoomWithName = meetingRoomRepository.findMeetingRoomByName(newMrName);
        if (!existingMeetingRoomWithName.isEmpty() && existingMeetingRoomWithName.get(0).getMrIndex() != mrIndex) {
            return false; // 다른 회의실에서 이미 사용 중인 이름이면 수정 실패
        }
        // 수정 가능한 필드 업데이트
        meetingRoom.setMrName(newMrName);
        meetingRoom.setMrCategory(newMrCategory);
        meetingRoomRepository.update(meetingRoom);
        return true; // 수정 성공
    }
    //Read- 카테고리로 MeetingRoom 찾기
    public List<MeetingRoom> findMeetingRoomsByCategory(String mrCategory) {
        return meetingRoomRepository.findMeetingRoomsByCategory(mrCategory);
    }
    //Read - 모든 MeetingRoom 찾기
    public List<MeetingRoom> findAllMeetingRooms() {
        return meetingRoomRepository.findAllMeetingRooms();
    }
    //Read - 모든 MeetingRoom의 카테고리 리스트 반환
    public List<String> findAllMeetingRoomCategories() {
        return meetingRoomRepository.findAllMeetingRooms().stream()
                .map(MeetingRoom::getMrCategory)
                .distinct()
                .collect(Collectors.toList());
    }
    // 추가적인 서비스 메소드들...
}

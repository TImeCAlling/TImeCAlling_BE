package TImeCAlling.spring.service.pushMessageSetting;

import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingRequestDTO;
import TImeCAlling.spring.web.dto.pushMessageSetting.PushMessageSettingResponseDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class PushMessageSettingCommandServiceTest {

    @Autowired
    private PushMessageSettingCommandService commandService;
    @Autowired
    private PushMessageSettingQueryService queryService;

    @Test
    @DisplayName("푸시 메세지 세팅 생성")
    @Rollback
    public void 푸시_메세지_세팅_생성() {
        // given
        Long userId = 1L;
        PushMessageSettingRequestDTO.CreateDTO request = new PushMessageSettingRequestDTO.CreateDTO(
            userId, 30, "example body", "example music", "url", true);

        // when
        PushMessageSettingResponseDTO.CreateDTO result = commandService.createPushMessageSetting(request);

        // then
        PushMessageSettingResponseDTO.DetailDTO target = queryService.getDetailPushMessageSetting(result.getId());
        assertThat(target.getUserId()).isEqualTo(request.getUserId());
        assertThat(target.getOffset()).isEqualTo(request.getOffset());
        assertThat(target.getBody()).isEqualTo(request.getBody());
        assertThat(target.getMusic()).isEqualTo(request.getMusic());
        assertThat(target.getMusicUrl()).isEqualTo(request.getMusicUrl());
        assertThat(target.getIsActive()).isEqualTo(request.getIsActive());
    }

    @Test
    @DisplayName("푸시 메세지 세팅 삭제")
    @Rollback
    public void 푸시_메세지_세팅_삭제() {
        // given
        Long userId = 1L;
        PushMessageSettingRequestDTO.CreateDTO createRequest = new PushMessageSettingRequestDTO.CreateDTO(
                userId, 30, "example body", "example music", "url", true);

        PushMessageSettingResponseDTO.CreateDTO created = commandService.createPushMessageSetting(createRequest);

        // when
        PushMessageSettingResponseDTO.DeleteDTO result = commandService.deletePushMessageSetting(created.getId());

        // then
        assertThat(result.getId()).isEqualTo(created.getId());
        assertThatThrownBy(() -> queryService.getDetailPushMessageSetting(created.getId()))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("푸시 메세지 바디 업데이트")
    @Rollback
    public void 푸시_메세지_바디_업데이트() {
        // given
        Long userId = 1L;
        PushMessageSettingRequestDTO.CreateDTO createRequest = new PushMessageSettingRequestDTO.CreateDTO(
                userId, 30, "example body", "example music", "url", true);

        PushMessageSettingResponseDTO.CreateDTO created = commandService.createPushMessageSetting(createRequest);

        PushMessageSettingRequestDTO.UpdateBodyDTO request = new PushMessageSettingRequestDTO
                .UpdateBodyDTO("updated body");

        // when
        PushMessageSettingResponseDTO.UpdateBodyDTO result = commandService.updatePushMessageSetting(created.getId(), request);

        // then
        PushMessageSettingResponseDTO.DetailDTO target = queryService.getDetailPushMessageSetting(created.getId());
        assertThat(target.getId()).isEqualTo(created.getId());
        assertThat(target.getBody()).isEqualTo(request.getBody());
    }

    @Test
    @DisplayName("푸시 메세지 음악 업데이트")
    @Rollback
    public void 푸시_메세지_음악_업데이트() {
        // given
        Long userId = 1L;
        PushMessageSettingRequestDTO.CreateDTO createRequest = new PushMessageSettingRequestDTO.CreateDTO(
                userId, 30, "example body", "example music", "url", true);

        PushMessageSettingResponseDTO.CreateDTO created = commandService.createPushMessageSetting(createRequest);

        PushMessageSettingRequestDTO.UpdateMusicDTO request = new PushMessageSettingRequestDTO.UpdateMusicDTO(
                "updated music", "updated url");

        // when
        PushMessageSettingResponseDTO.UpdateMusicDTO result = commandService.updatePushMessageSetting(created.getId(), request);

        // then
        PushMessageSettingResponseDTO.DetailDTO target = queryService.getDetailPushMessageSetting(created.getId());
        assertThat(target.getId()).isEqualTo(created.getId());
        assertThat(target.getMusic()).isEqualTo(request.getMusic());
        assertThat(target.getMusicUrl()).isEqualTo(request.getMusicUrl());
    }

    @Test
    @DisplayName("푸시 메세지 활성화 상태 업데이트")
    @Rollback
    public void 푸시_메세지_활성화_상태_업데이트() {
        // given
        Long userId = 1L;
        PushMessageSettingRequestDTO.CreateDTO createRequest = new PushMessageSettingRequestDTO.CreateDTO(
                userId, 30, "example body", "example music", "url", true);

        PushMessageSettingResponseDTO.CreateDTO created = commandService.createPushMessageSetting(createRequest);

        PushMessageSettingRequestDTO.UpdateIsActiveDTO request = new PushMessageSettingRequestDTO.UpdateIsActiveDTO(
                false);

        // when
        PushMessageSettingResponseDTO.UpdateIsActiveDTO result =
                commandService.updatePushMessageSetting(created.getId(), request);

        // then
        PushMessageSettingResponseDTO.DetailDTO target = queryService.getDetailPushMessageSetting(created.getId());
        assertThat(target.getId()).isEqualTo(created.getId());
        assertThat(target.getIsActive()).isEqualTo(request.getIsActive());
    }
}





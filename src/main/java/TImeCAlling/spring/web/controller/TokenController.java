package TImeCAlling.spring.web.controller;

import TImeCAlling.spring.auth.JwtUtil;
import TImeCAlling.spring.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class TokenController {

    private final JwtUtil jwtUtil;

    @Operation(summary = "JWT 토큰 생성", description = "생성된 토큰 반환")
    @PostMapping("/createJWT")
    public String createJWT(@RequestParam Long userId, String nickname) {
        String token = jwtUtil.createAccessToken(userId, nickname, 1000 * 60 * 30L);
        return token;
    }

    @Operation(summary = "토큰 테스트", description = "해당 user의 id 반환")
    @GetMapping("/testJWT")
    public Long testToken(@AuthenticationPrincipal User user) {
        return user.getId();
    }



}

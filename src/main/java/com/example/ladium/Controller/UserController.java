package com.example.ladium.Controller;

import com.example.ladium.Dto.TokenRequestDTO;
import com.example.ladium.Dto.UserDTO;
import com.example.ladium.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.ladium.Util.ApiUtil.*;
import static com.example.ladium.Util.ApiUtil.success;
import static com.example.ladium.Util.ApiUtil.error;


@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/signup")
    public ApiResult<?> signup(@RequestBody UserDTO userDTO){
        try {
            return success(userService.signup(userDTO));
        } catch (Exception e) {
            return error(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ApiResult<?> login(@RequestBody UserDTO userDTO){
        try {
            return success(userService.login(userDTO));
        } catch (Exception e){
            return error(e, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/reissue")
    public ApiResult<?> reissue(@RequestBody TokenRequestDTO tokenRequestDTO) {
        try {
            return success(userService.reissue(tokenRequestDTO));
        } catch (Exception e) {
            return error("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ApiResult<?> logout(){//@RequestHeader Authentication accessToken){
        try{
            userService.logout();
            return success("logout success");
        } catch (Exception e){
            return error(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/withdrawal")
    public ApiResult<?> deleteUser(){
        try {
            userService.deleteUser();
            return success("withdrawal success");
        } catch (Exception e){
            return error(e, HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<TokenResponseDTO> authorize(@Valid @RequestBody UserDTO userDTO) {
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(userDTO.getUser_id(), userDTO.getPassword());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        TokenResponseDTO jwt = tokenProvider.createToken(authentication);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt.getAccessToken());
//
//        return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
//    }

//    @GetMapping("/user")
//    public ResponseEntity<User_info> getMyUserInfo(){
//        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
//    }

}

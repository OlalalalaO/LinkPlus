package us.linkpl.linkplus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import us.linkpl.linkplus.entity.Account;
import us.linkpl.linkplus.entity.AccountSocialmedia;
import us.linkpl.linkplus.entity.response.Media;
import us.linkpl.linkplus.mapper.AccountMapper;
import us.linkpl.linkplus.mapper.AccountSocialmediaMapper;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author samsara
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/api/account-socialmedia")
public class AccountSocialmediaController {

    @Autowired
    AccountSocialmediaMapper accountSocialmediaMapper;

    @Autowired
    AccountMapper accountMapper;

    /**
     * 修改社交媒体
     *
     * @param media
     * @return
     */
    @PostMapping("")
    public ResponseEntity<String> editAccountSocialMedia(@CookieValue("id") String id, @RequestBody Media media) {
        if (media == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        AccountSocialmedia a = new AccountSocialmedia();
        Integer accountId = Integer.valueOf(id);
        AccountSocialmedia accountSocialmedia = accountSocialmediaMapper.selectById(media.getId());
        if (accountSocialmedia.getAccountId() != accountId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        a.setId(media.getId());
        a.setContent(media.getContent());
        accountSocialmediaMapper.updateById(a);
        return ResponseEntity.ok("Edit Account Social Media Successfully");
    }

    /**
     * 添加社交媒体
     *
     * @param medias
     * @return
     */
    @PutMapping("")
    public ResponseEntity<String> addAccountSocialMedia(@RequestBody List<Media> medias, @CookieValue("id") String id) {
        if (medias == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        for (Media media : medias) {
            if(media.getMediaId()==null||media.getContent()==null){
                continue;
            }
            //添加
            if (media.getId() == null) {
                AccountSocialmedia a = new AccountSocialmedia();
                Integer accountId = Integer.valueOf(id);
                a.setAccountId(accountId);
                a.setSocialMediaId(Math.toIntExact(media.getMediaId()));
                a.setContent(media.getContent());
                accountSocialmediaMapper.insert(a);
                Account account = accountMapper.selectById(accountId);
                account.setDisplayNumber(account.getDisplayNumber() + 1);
                accountMapper.updateById(account);
                //修改
            } else {
                AccountSocialmedia a = new AccountSocialmedia();
                Integer accountId = Integer.valueOf(id);
                AccountSocialmedia accountSocialmedia = accountSocialmediaMapper.selectById(media.getId());
                if (accountSocialmedia == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
                if (!accountSocialmedia.getAccountId().equals(accountId)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
                a.setId(media.getId());
                a.setContent(media.getContent());
                accountSocialmediaMapper.updateById(a);
            }
        }
        return ResponseEntity.ok("OK");
    }
}

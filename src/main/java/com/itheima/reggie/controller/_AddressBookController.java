/*
package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

*
 * 地址簿管理


@Slf4j
@RestController
@RequestMapping("/api/addressBook")
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;

*
     * 新增
     * @param addressBook
     * @return


    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook: {}", addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

*
     * 根据id查询地址
     * @param id
     * @return


    @GetMapping
    public R<AddressBook> get(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        }
        return R.error("请添加地址");
    }

*
     * 设置默认地址
     * @param addressBook
     * @return


    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        log.info("addressBook: {}", addressBook);
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        updateWrapper.set(AddressBook::getIsDefault, 0);
        //SQL: update address_book set is default = 0 where user_id = ?
        addressBookService.update(updateWrapper);

        addressBook.setIsDefault(1);
        //SQL: update address_book set is_default = 1 where id = ?
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }

*
     * 查询默认地址
     * @return


    @GetMapping("default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault, 1);

        //SQL: select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        if (addressBook == null) {
            return R.error("还没有设置默认地址");
        }
        return R.success(addressBook);
    }

*
     * 查询指定用户的全部地址
     * @param addressBook
     * @return


    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook: {}", addressBook);

        //Long userId = addressBook.getUserId();
        //条件构造器
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(addressBook.getUserId() != null, AddressBook::getUserId, addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        //SQL: select * from address_book where user_id = ? order by update_time desc
        List<AddressBook> addressBookList = addressBookService.list(queryWrapper);
        return R.success(addressBookList);
    }

*
     * 通过id获取修改地址数据
     * @param id
     * @return


    @GetMapping("/{id}")
    public R<AddressBook> getAddressById(@PathVariable Long id){
        log.info("addressBook: {}", id);

        return R.success(addressBookService.getById(id));
    }

    @PutMapping
    public R<AddressBook> update(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook: {}", addressBook);
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }

    @DeleteMapping()
    public R<String> delete(@RequestParam("ids") Long id){
        log.info("id: {}", id);
        addressBookService.removeById(id);
        return R.success("");
    }
}
*/

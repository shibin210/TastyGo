package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.repository.SetmealRepository;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Setmeal Service Implementation (Hibernate Version)
 */
@Service
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealRepository setmealRepository;

    @Override
    public void saveSetmeal(Setmeal setmeal) {
        setmealRepository.save(setmeal);
    }

    @Override
    public Optional<Setmeal> getSetmealById(Long id) {
        return setmealRepository.findById(id);
    }

    @Override
    public void updateSetmeal(Setmeal setmeal) {
        setmealRepository.save(setmeal);
    }

    @Override
    public boolean updateStatusBatch(List<Long> ids, Integer status) {
        if (ids == null || ids.isEmpty() || status == null) {
            return false;
        }
        for (Long id : ids) {
            Optional<Setmeal> optionalSetmeal = setmealRepository.findById(id);
            optionalSetmeal.ifPresent(setmeal -> {
                setmeal.setStatus(status);
                setmealRepository.save(setmeal);
            });
        }
        return true;
    }

    @Override
    public List<Setmeal> listSetmealsByCategory(Long categoryId) {
        return setmealRepository.findByCategoryId(categoryId);
    }

    @Override
    public void deleteSetmeals(List<Long> ids) {
        setmealRepository.deleteAllById(ids);
    }

    @Override
    public int countSetmealsByCategoryId(Long categoryId) {
        return setmealRepository.countByCategoryId(categoryId);
    }
}

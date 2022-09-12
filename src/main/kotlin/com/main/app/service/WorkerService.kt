package com.main.app.service

import com.main.app.entity.Worker
import com.main.app.model.CreateWorkerRequestModel
import com.main.app.model.SimpleWorkerModel
import com.main.app.repository.WorkerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class WorkerService {
    @Autowired
    private lateinit var workerRepository: WorkerRepository

    @Transactional
    fun createWorker(model : CreateWorkerRequestModel) : Boolean{
        return try{
            workerRepository.save(Worker(model.id,model.pwd,model.name,model.email))
            true
        }catch (e: Exception){
            false
        }
    }

    @Transactional
    fun activateWorker(no : Long) : Boolean{
        return try {
            val count = workerRepository.updateWorkerStatus(no, true)
            return count>0
        }catch (e:Exception){
            false
        }
    }

    @Transactional
    fun deActivateWorker(no : Long) : Boolean{
        return try {
            val count = workerRepository.updateWorkerStatus(no, false)
            return count>0
        }catch (e:Exception){
            false
        }
    }

    fun getActivateWorker(id : String, pwd : String) : SimpleWorkerModel?{
        return try {
            val worker = workerRepository.findWorkerByIdAndPwdAndIsUsedIsTrue(id,pwd)
            if(worker!=null){
                SimpleWorkerModel(worker.getNo(),worker.getId(),worker.getName(),worker.getEmail())
            }
            else {
                null
            }
        }catch (e:Exception){
            null
        }
    }

    fun getActivateWorkerList() : List<SimpleWorkerModel>?{
        return try {
            workerRepository.findActivatedWorker().map { worker -> SimpleWorkerModel(worker.getNo(),worker.getId(),worker.getName(),worker.getEmail()) }
        }catch (e:Exception){
            null
        }
    }

    fun searchWorkerByName(name : String) : List<SimpleWorkerModel>? {
        return try {
            workerRepository.findWorkersByNameContaining(name).map { worker -> SimpleWorkerModel(worker.getNo(),worker.getId(),worker.getName(),worker.getEmail()) }
        }catch (e:Exception){
            null
        }
    }

    @Transactional
    fun deleteWorkerById(no: Long) : Boolean{
        return try {
            workerRepository.deleteById(no)
            true
        }catch (e: Exception){
            false
        }
    }
}
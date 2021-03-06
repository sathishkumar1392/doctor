package com.task.doctor

import com.task.data.common.Connectivity
import com.task.doctor.data.DoctorRepositoryImpl
import com.task.doctor.domain.model.Result
import com.task.doctor.model.DoctorResponseModel
import com.task.doctor.network.DoctorService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

/*
 * Copyright (c) 2019. Created for Coding Challenge and Created by R Sathish Kumar on 10-01-2020.
 * All Rights Reserved,Company Confidential.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name : doctor
 * Created by : R Sathish Kumar - Android Application Developer
 * Created on :10-01-2020 
 * File Name : DoctorRepositoryImplTest.kt
 * ClassName : DoctorRepositoryImplTest
 * Module Name : app
 * Desc : 
 */

class DoctorRepositoryImplTest:BaseTest() {

 @Mock
 private lateinit var doctorService:DoctorService

 @Mock
 private lateinit var connectivity: Connectivity

 @InjectMocks
 private lateinit var repo: DoctorRepositoryImpl

   @Before
   fun init(){
      MockitoAnnotations.initMocks(this)
      repo = DoctorRepositoryImpl(doctorService,connectivity)
   }

   @Test
   fun getDoctorListFromNetworkTest_Success() {
      val result = Response.success<DoctorResponseModel>(DoctorResponseModel())
      result.code()

      runBlocking {
         `when`(connectivity.isNetworkAvailable()).thenReturn(true)
         `when`(doctorService.listDoctors()).thenReturn(result)
         val response = doctorService.listDoctors()
         Assert.assertEquals(result,response)
      }
   }

   @Test
   fun getDoctorListFromNetworkTest_Failure() {
      val result = Result.Error("No Internet Connection")

      runBlocking {
         `when`(!connectivity.isNetworkAvailable()).thenReturn(false)
         val response = Result.Error("No Internet Connection")
         Assert.assertEquals(result,response)
      }
   }

   @Test
   fun getDoctorListWithKeyTest_Success() {runBlocking {
         val lastkey = "CvQD7gEAAKjcb"

         val result = Response.success<DoctorResponseModel>(DoctorResponseModel())
         result.code()
         `when`(connectivity.isNetworkAvailable()).thenReturn(true)
         `when`(doctorService.listDoctorsLastKey(lastkey)).thenReturn(result)
         val response = doctorService.listDoctorsLastKey(lastkey)
         Assert.assertEquals(result,response)
      }
   }
}
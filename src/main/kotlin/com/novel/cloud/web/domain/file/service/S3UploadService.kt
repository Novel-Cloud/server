package com.novel.cloud.web.domain.file.service


import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.*

@Service
class S3UploadService(
    private val s3Client: AmazonS3Client
) {

    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucket: String

    @Value("\${cloud.aws.s3.dir}")
    lateinit var dir: String

    /**
     * s3 업로드
     */
    @Throws(IOException::class)
    fun upload(file: MultipartFile): String {
        val fileName = UUID.randomUUID().toString() + "-" + file.originalFilename
        val objMeta = ObjectMetadata()

        val bytes = IOUtils.toByteArray(file.inputStream)

        objMeta.contentType = file.contentType
        objMeta.contentLength = bytes.size.toLong()

        val byteArrayIs = ByteArrayInputStream(bytes)

        s3Client.putObject(PutObjectRequest(bucket, dir + fileName, byteArrayIs, objMeta)
            .withCannedAcl(CannedAccessControlList.PublicRead))

        return s3Client.getUrl(bucket, dir + fileName).toString()
    }

}
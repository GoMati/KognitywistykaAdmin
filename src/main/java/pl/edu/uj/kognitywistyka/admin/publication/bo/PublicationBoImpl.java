package pl.edu.uj.kognitywistyka.admin.publication.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import pl.edu.uj.kognitywistyka.admin.publication.dao.PublicationDao;
import pl.edu.uj.kognitywistyka.admin.publication.model.Publication;
import pl.edu.uj.kognitywistyka.admin.publication.model.Tag;
import pl.edu.uj.kognitywistyka.admin.util.PropertiesReader;

public class PublicationBoImpl implements PublicationBo, Serializable {
	private static final long serialVersionUID = 1L;
	PublicationDao publicationDao;

	public void setPublicationDao(PublicationDao publicationDao) {
		this.publicationDao = publicationDao;
	}

	public void addPublication(Publication publication,
			UploadedFile uploadedDocument, String tags) {
		System.err.println("Bo.add: "+ uploadedDocument.getName());
		if(uploadedDocument!=null)
			publication.setFileName(serveDocument(uploadedDocument));
		else
			publication.setFileName("");
		
		Set<Tag> workingSet = new HashSet<Tag>(0);
		for(String i : tags.split(", "))
		{
			Tag tag = new Tag();
			tag.setTitle(i);
			workingSet.add(tag);
		}
		publicationDao.addPublicationWithTags(publication, workingSet);
	}

	public void updatePublication(Publication publication,
			UploadedFile uploadedDocument, String tags) {
		publicationDao.removePublication(publication.getPublicationId());
		if(uploadedDocument!=null)
			publication.setFileName(serveDocument(uploadedDocument));
		else
			publication.setFileName("");
		
		Set<Tag> workingSet = new HashSet<Tag>(0);
		for(String i : tags.split(", "))
		{
			Tag tag = new Tag();
			tag.setTitle(i);
			workingSet.add(tag);
		}
		publication.setTags(workingSet);
		publicationDao.addPublication(publication);
	}

	public void removePublication(Publication publication) {
		publicationDao.removePublication(publication);

	}

	public void removePublication(long publicationId) {
		publicationDao.removePublication(publicationId);
	}

	public List<Publication> findAlPublications() {
		return publicationDao.findAllPublication();
	}

	public Publication getPublication(long publicationId) {
		return publicationDao.getPublication(publicationId);
	}

	private String serveDocument(UploadedFile uploadedDocument) {
		try {
			System.err.println("bo.serve"+ uploadedDocument.getName());
			String filename = System.currentTimeMillis()
					+ uploadedDocument.getName();

			File destFile = new File(
					PropertiesReader.getPathToStoreFile()
							+ PropertiesReader
									.getPropertyOfPublication("pathToPublication")
							+ filename);

			FileOutputStream fop = new FileOutputStream(destFile);
			fop.write(uploadedDocument.getBytes());
			fop.flush();
			fop.close();

			return filename;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}

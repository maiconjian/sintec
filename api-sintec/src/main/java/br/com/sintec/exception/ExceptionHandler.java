package br.com.sintec.exception;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String mensagemUsuario = "Erro";
		String mensagemDesenvolvedor = ex.getMessage();

		List<Error> erros = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));

		System.out.println(mensagemDesenvolvedor);

		return handleExceptionInternal(ex, erros, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
	  NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagemUsuario = "Erro";
		String mensagemDesenvolvedor = ex.getMessage();

		List<Error> erros = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));

		System.out.println(mensagemDesenvolvedor);

		return handleExceptionInternal(ex, erros, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
//	@org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
//	public ResponseEntity<Object> handleAll(Exception ex,HttpHeaders headers, HttpStatus status, WebRequest request) {
//		String mensagemUsuario = "Erro";
//		String mensagemDesenvolvedor = ex.getMessage();
//
//		List<Error> erros = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));
//
//		System.out.println(mensagemDesenvolvedor);
//
//		return handleExceptionInternal(ex, erros, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
//	}

	@org.springframework.web.bind.annotation.ExceptionHandler({DataIntegrityViolationException.class })
	public ResponseEntity<Object> handlerDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		int codigoErroUnique = 2627;
		String mensagemUsuario = null;
		String mensagemDesenvolvedor = null;

		if (ex.getCause().getCause() instanceof SQLException) {
			SQLException e = (SQLException) ex.getCause().getCause();
			if (e.getErrorCode() == codigoErroUnique) {
				int indexInicio = e.getMessage().indexOf("(") + 1;
				int indexFim = e.getMessage().indexOf(")");
				mensagemUsuario = "Informacao ja cadastrada no sistema: "
						+ e.getMessage().substring(indexInicio, indexFim);
				mensagemDesenvolvedor = e.getMessage();
			} else {
				mensagemUsuario = "Erro ";
				mensagemDesenvolvedor = e.getMessage();
			}
		} else {
			mensagemUsuario = "Erro ";
			mensagemDesenvolvedor = ex.getMessage();
		}

		System.out.println(mensagemDesenvolvedor);
		List<Error> erros = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	public ResponseEntity<Object> handleCustomRuntimeException(CustomRuntimeException ex, HttpHeaders headers,
			WebRequest request) {
		String mensagemUsuario = ex.getMessage();
		String mensagemDesenvolvedor = "";

		List<Error> erros = Arrays.asList(new Error(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.CONFLICT, request);
	}

	public static class Error {

		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Error(String mensagemUsuario, String mensagemDesenvolvedor) {
			super();
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public Error(String mensagemUsuario) {
			super();
			this.mensagemUsuario = mensagemUsuario;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}

	}
}

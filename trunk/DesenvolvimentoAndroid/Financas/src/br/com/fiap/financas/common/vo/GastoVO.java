package br.com.fiap.financas.common.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.util.Log;

public class GastoVO implements Serializable {
	
	private static final long serialVersionUID = 55162102414220105L;

	public final static int GASTO = 2;	
	
	private Integer id;
	private String descricao;
	private Double valor;
	private Date data;
	private List<CategoriaVO> categorias;	
	private Integer parcela;
	private Integer numParcelas;
	private GanhoVO ganhoDescontar;
	private String local;
	private Double latitude;
	private Double longitude;
	private String foto;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getDataFormatted() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(data);
	}

	public void setData(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.data = sdf.parse(data);
		}
		catch (Exception e) {
			Log.e("erro", e.getMessage());
		}
	}	
	
	public List<CategoriaVO> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaVO> categorias) {
		this.categorias = categorias;
	}
	
	public String getCategoriasString() {
		String strCategorias = "";
		for (CategoriaVO catVO : categorias) {
			if (strCategorias == "")
				strCategorias = catVO.getDescricao();
			else
				strCategorias = strCategorias + " / " +catVO.getDescricao();				
		}
		return strCategorias;
	}

	public Integer getParcela() {
		return parcela;
	}
	
	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}
	
	
	public Integer getNumParcelas() {
		return numParcelas;
	}
	
	public void setNumParcelas(Integer numParcelas) {
		this.numParcelas = numParcelas;
	}

	public GanhoVO getGanhoDescontar() {
		return ganhoDescontar;
	}

	public void setGanhoDescontar(GanhoVO ganhoDescontar) {
		this.ganhoDescontar = ganhoDescontar;
	}

	public String getLocal() {
		return local;
	}
	
	public void setLocal(String local) {
		this.local = local;
	}
	
	
	public Double getLatitude() {
		if (!local.equals("") && latitude == null){
			
			String lat = local.substring(0,local.indexOf(","));
			
			Log.i("GPS", "latitude: " + lat);
			
			latitude = Double.parseDouble(lat);

		}
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {

		if (!local.equals("") && longitude == null){
			
			String lng = local.substring(local.indexOf(",")+2,local.length());
			
			Log.i("GPS", "longitude: " + lng);
			
			longitude = Double.parseDouble(lng);
		}
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	

}

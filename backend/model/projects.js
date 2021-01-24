module.exports = (sequelize, Seq) => {
	return sequelize.define('project', {
		id : {
			type : Seq.INTEGER,
			primaryKey: true,
			autoIncrement: true,
		},
		userId : Seq.INTEGER,
		title: Seq.STRING,
		summary : Seq.TEXT,
		privacy : Seq.BOOLEAN,
		status : Seq.BOOLEAN,
		requirements : Seq.TEXT
	}, 
	{

		
		timestamps : true
	})
}

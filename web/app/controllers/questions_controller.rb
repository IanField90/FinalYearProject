class QuestionsController < ApplicationController
  # GET /questions
  def index
    @questions = Question.all
  end
  
  # GET /questions/1
  def show
    @question = Question.find(params[:id])
    @options = @question.options
    @type = Type.find(@question.type_id)
    respond_to do |format|
      format.html
      format.json { render :json => @question }
    end
  end

  # GET /quizzes/1/questions/new
  def new
    @quiz = Quiz.find(params[:quiz_id])
    @question = Question.new(params[:question])
    @types = Type.all
    if !is_user_admin
      redirect_to @question, :notice => "Cannot create unless admin."
    end
  end
  
  # GET /questions/1/edit
  def edit
    @question = Question.find(params[:id])
    @types = Type.all
    if !is_user_admin
      redirect_to @question, :notice => "Cannot edit unless admin."
    end
  end
  
  # POST /quizzes/1/questions
  def create
    if is_user_admin
      @question = Question.new(params[:question])
      @question.type_id = params[:type][:id]
      if @question.save
        redirect_to @question, :notice => "Question was successfully created."
      else
        render :action => "new"
      end
    else
      redirect_to quizzes_path
    end
  end
  
  # PUT /questions/1
  def update
    @question = Question.find(params[:id])
    if is_user_admin
      if @question.update_attributes(params[:question])
        redirect_to @question, :notice => "Question was successfully updated."
      else
        render :action => "edit"
    else
      redirect_to quiz_path(@question.quiz_id)
    end
  end
  
  # DELETE /questions/1
  def destroy
    @question = Question.find(params[:id])
    @id = @question.quiz_id
    if is_user_admin
      @question.destroy
    end
    redirect_to quiz_path(@id)
  end

end
